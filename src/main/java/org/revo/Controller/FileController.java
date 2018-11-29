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

@RestController
@RequestMapping("api/file")
@Slf4j
public class FileController {
    @Autowired
    public FileService fileService;
    @Autowired
    public Processor processor;

    @PostMapping("save")
    public void save(@RequestBody File file) {
        File save = fileService.save(file);
        log.info("send file_queue " + file.getId());
        processor.file_queue().send(MessageBuilder.withPayload(save).build());
    }
}
