package org.revo.Controller;

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
public class FileController {
    @Autowired
    public FileService fileService;
    @Autowired
    public Processor processor;

    @PostMapping("save")
    public void save(@RequestBody File file) {
        processor.file_queue().send(MessageBuilder.withPayload(fileService.save(file)).build());
//        processor.ToFeedBack_push().send(MessageBuilder.withPayload(new Stater(((Base) saved), Queue.FILE, State.QUEUED)).build());
    }
}
