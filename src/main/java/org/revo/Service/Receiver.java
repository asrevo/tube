package org.revo.Service;

import lombok.extern.slf4j.Slf4j;
import org.revo.Config.Processor;
import org.revo.Domain.Index;
import org.revo.Domain.Master;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.Message;
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
        log.info("receive tube_hls ");
        indexService.save(index.getPayload()).
                flatMapMany(it -> masterService.append(Mono.just(it))).subscribe();
    }

    @StreamListener(value = Processor.tube_info)
    @Output(value = Processor.feedback_index)
    public Flux<Master> info(Message<Master> master) {
        log.info("receive tube_info ");
        return masterService.saveInfo(master.getPayload())
                .flatMapMany(Mono::just);
    }

    @StreamListener(value = Processor.tube_store)
    @Output(value = Processor.ffmpeg_queue)
    public Flux<Master> store(Message<Master> master) {
        log.info("receive tube_store ");
        Flux<Master> save = masterService.save(master.getPayload()).flatMapMany(Mono::just);
        log.info("send ffmpeg_queue ");
        return save;
    }
}
