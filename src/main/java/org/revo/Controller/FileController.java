package org.revo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.revo.Config.Processor;
import org.revo.Domain.File;
import org.revo.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

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
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(serverRequest.bodyToMono(File.class), File.class);
                })
                .build();
    }

}
