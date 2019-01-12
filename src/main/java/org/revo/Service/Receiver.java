package org.revo.Service;

import lombok.extern.slf4j.Slf4j;
import org.revo.Config.Processor;
import org.revo.Domain.File;
import org.revo.Domain.Index;
import org.revo.Domain.Master;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import reactor.core.publisher.Mono;

/**
 * Created by ashraf on 23/04/17.
 */
@MessageEndpoint
@Slf4j
public class Receiver {
    @Autowired
    private MasterService masterService;
    @Autowired
    private IndexService indexService;

    @StreamListener(value = Processor.tube_hls)
    public void hls(Message<Index> index) {
        log.info("receive tube_hls " + index.getPayload().getId());
        masterService.append(indexService.save(index.getPayload())).subscribe();
    }

    @StreamListener(value = Processor.tube_info)
    @SendTo(value = Processor.feedback_index)
    public Mono<Master> info(Message<Master> master) {
        log.info("receive tube_info " + master.getPayload().getId());
        return masterService.saveInfo(master.getPayload());
    }

    @StreamListener(value = Processor.tube_store)
    @SendTo(value = Processor.ffmpeg_queue)
    public Mono<Master> store(Message<Master> master) {
        log.info("receive tube_store " + master.getPayload().getId());
        Mono<Master> save = masterService.save(master.getPayload());
//        log.info("send ffmpeg_queue " + save.getId());
        return save;
    }

    @SqsListener("tube_file_queue")
    public void queueListener(Message<File> fileMessage) {
        log.info("receive tube_file_queue " + fileMessage.getPayload().getId());
    }
}
