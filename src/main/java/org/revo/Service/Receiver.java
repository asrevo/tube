package org.revo.Service;

import lombok.extern.slf4j.Slf4j;
import org.revo.Config.Processor;
import org.revo.Domain.Index;
import org.revo.Domain.Master;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import reactor.core.publisher.Flux;
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
    public Flux<Master> info(Mono<Master> master) {
        log.info("receive tube_info ");
        return master.flatMapMany(it -> masterService.saveInfo(it));
    }

    @StreamListener(value = Processor.tube_store)
    @SendTo(value = Processor.ffmpeg_queue)
    public Flux<Master> store(Mono<Master> master) {
        log.info("receive tube_store ");
        Flux<Master> save = master.flatMapMany(it -> masterService.save(it));
        log.info("send ffmpeg_queue ");
        return save;
    }
}
