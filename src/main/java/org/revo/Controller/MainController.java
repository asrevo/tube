package org.revo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.revo.Config.Processor;
import org.revo.Domain.File;
import org.revo.Service.FileService;
import org.revo.Service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

//@RestController
//@RequestMapping("api/file")
@Slf4j
@Configuration
public class MainController {
    @Autowired
    public FileService fileService;
    @Autowired
    public Processor processor;
    @Autowired
    private MasterService masterService;

    @Bean
    public RouterFunction<ServerResponse> function() {
        return nest(path("/api/file"), route(POST("/save"), serverRequest -> {
            return ok().body(serverRequest.bodyToMono(File.class)/*.flatMap(it -> fileService.save(it))*/, File.class)
                    ;
        }))
/*
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
                )
*/
                ;
    }

}
