package org.revo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.revo.Config.Processor;
import org.revo.Domain.File;
import org.revo.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

//import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/file")
@Slf4j
public class FileController {
    @Autowired
    public FileService fileService;
    @Autowired
    public Processor processor;

    @PostMapping("save")
    public Mono<Message<File>> save(@RequestBody File file, ServerHttpRequest request) {
        return fileService.save(file).map(MessageBuilder::withPayload)
                .map(MessageBuilder::build)
                .doOnEach(it -> processor.file_queue().send(it.get()));
    }
}
