package org.revo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.revo.Config.Processor;
import org.revo.Domain.File;
import org.revo.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

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
        return route()
                .POST("api/file/save", serverRequest -> {
                    Mono<Void> p = serverRequest.bodyToMono(File.class).flatMap(it -> fileService.save(it)).then();
                    return ok().build(p);
                })
                .build();
    }

}
