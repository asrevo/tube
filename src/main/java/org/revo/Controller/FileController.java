package org.revo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.revo.Config.Processor;
import org.revo.Domain.File;
import org.revo.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

//@RestController
//@RequestMapping("api/file")
@Slf4j
@Configuration
public class FileController {
    @Autowired
    public FileService fileService;
    @Autowired
    public Processor processor;

    @Bean
    public RouterFunction<ServerResponse> function() {

//        return nest(path("/person"),
//                nest(accept(APPLICATION_JSON),
//                        route(GET("/{id}"), handler::getPerson)
//                                .andRoute(method(HttpMethod.GET), handler::allPeople).andRoute(POST("/").and(contentType(APPLICATION_JSON)), handler::savePerson))
//                );
//
        return route(
                POST("/api/file/save"), serverRequest -> ok().body(serverRequest.bodyToMono(File.class).flatMap(it -> fileService.save(it))
                .doOnNext(it -> processor.file_queue().send(MessageBuilder.withPayload(it).build()))
                .then(), Void.class)

        );
    }

}
