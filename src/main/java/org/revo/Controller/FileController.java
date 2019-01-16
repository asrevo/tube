package org.revo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.revo.Config.Processor;
import org.revo.Domain.File;
import org.revo.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("api/file")
@Slf4j
public class FileController {
    @Autowired
    public FileService fileService;
    @Autowired
    public Processor processor;

    @PostMapping("save")
    public Mono<Void> save(@RequestBody File file) {
        return fileService.save(file)
                .publishOn(Schedulers.elastic())
                .map(MessageBuilder::withPayload)
                .map(MessageBuilder::build)
                .doOnNext(it -> processor.file_queue().send(it)).then();
    }
}
