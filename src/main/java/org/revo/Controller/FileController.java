package org.revo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.revo.Config.Processor;
import org.revo.Domain.File;
import org.revo.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/file")
@Slf4j
public class FileController {
    @Autowired
    public FileService fileService;
    @Autowired
    public Processor processor;
    @Autowired
    public QueueMessagingTemplate template;

/*
    @PostMapping("save")
    public void save(@RequestBody File file, HttpServletRequest request) {
        file.setIp(request.getHeader("X-FORWARDED-FOR"));
        Message<File> fileMessage = MessageBuilder.withPayload(fileService.save(file)).build();
        template.convertAndSend("lambda_file_queue", fileMessage);
        if (fileMessage.getPayload().getUrl().startsWith("http")) {
            log.info("send file_queue " + fileMessage.getPayload().getId());
            processor.file_queue().send(fileMessage);

        } else if (fileMessage.getPayload().getUrl().startsWith("magnet")) {
            log.info("send torrent_queue " + fileMessage.getPayload().getId());
            processor.torrent_queue().send(fileMessage);
        } else {
            log.info("send to unknown");
        }
    }
*/
}
