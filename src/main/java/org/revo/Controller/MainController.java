package org.revo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.revo.Config.Processor;
import org.revo.Domain.File;
import org.revo.Domain.Ids;
import org.revo.Domain.Master;
import org.revo.Domain.Status;
import org.revo.Service.FileService;
import org.revo.Service.IndexService;
import org.revo.Service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Arrays;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Configuration
public class MainController {
    @Autowired
    public FileService fileService;
    @Autowired
    public Processor processor;
    @Autowired
    private MasterService masterService;
    @Autowired
    private IndexService indexService;

    private final String masterURL = "/{master}.m3u8";
    private final String indexUrl = masterURL + "/{index}.m3u8";
    private final String keyUrl = masterURL + "/{master_id}.key";


    @Bean
    public RouterFunction<ServerResponse> function() {
        return nest(path("/api/file"), route(POST("/save"), serverRequest -> {

            return ok().body(serverRequest.bodyToMono(File.class)
                            .map(it -> {
                                it.setIp(serverRequest.exchange().getRequest().getHeaders().get("X-FORWARDED-FOR").get(0));
                                return it;
                            })
                            .flatMap(it -> fileService.save(it))
                            .doOnNext(it -> {
                                processor.file_queue().send(MessageBuilder.withPayload(it).build());
                            }).then()
                    , Void.class);
        }))
                .andNest(path("/api/master"),
                        route(GET("/{size}/{id}"), serverRequest -> {
                            Integer size = Integer.valueOf(serverRequest.pathVariable("size"));
                            String id = serverRequest.pathVariable("id");
                            if (id.equals("0")) id = null;
                            return ok().body(masterService.findAll(Status.SUCCESS, size, id, new Ids(), new Ids()), Master.class);
                        })
                                .andRoute(POST("/{size}/{id}"), serverRequest -> ok().body(serverRequest.bodyToMono(Ids.class).flatMapMany(it -> {
                                    Integer size = Integer.valueOf(serverRequest.pathVariable("size"));
                                    String id = serverRequest.pathVariable("id");
                                    return masterService.findAll(Status.SUCCESS, size, id, it, new Ids());
                                }), Master.class))
                                .andRoute(POST("/"), serverRequest -> ok().body(serverRequest.bodyToMono(Ids.class).flatMapMany(it -> {
                                    return masterService.findAll(Status.SUCCESS, 1000, null, new Ids(), it);
                                }), Master.class))
                                .andRoute(GET("/user/{id}"), serverRequest -> {
                                    String id = serverRequest.pathVariable("id");
                                    Ids ids = new Ids();
                                    ids.setIds(Arrays.asList(id));
                                    return ok().body(masterService.findAll(Status.SUCCESS, 1000, null, ids, new Ids()), Master.class);
                                })
                                .andRoute(GET("/one/{id}"), serverRequest -> {
                                    String id = serverRequest.pathVariable("id");
                                    return ok().body(masterService.findOne(id), Master.class);
                                })
                                .andRoute(GET(masterURL), serverRequest -> {
                                    String master = serverRequest.pathVariable("master");
                                    return ok()
                                            .header("Content-Type", "application/x-mpegURL")
                                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + master + ".m3u8")
                                            .body(masterService.getStream(master).map(IOUtils::toInputStream).map(InputStreamResource::new), InputStreamResource.class);
                                })
                                .andRoute(GET(indexUrl), serverRequest -> {
                                    String master = serverRequest.pathVariable("master");
                                    String index = serverRequest.pathVariable("index");
                                    return ok()
                                            .header("Content-Type", "application/x-mpegURL")
                                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + master + ".m3u8")
                                            .body(indexService.findOneParsed(master, index).map(IOUtils::toInputStream).map(InputStreamResource::new), InputStreamResource.class);
                                })
                                .andRoute(GET(keyUrl), serverRequest -> {
                                    String master = serverRequest.pathVariable("master");
                                    return ok()
                                            .header("Content-Type", "application/pgp-keys")
                                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + master + ".key")
                                            .body(masterService.findOne(master).map(Master::getSecret).map(IOUtils::toInputStream).map(InputStreamResource::new), InputStreamResource.class);
                                })
                )
                ;
    }

}
