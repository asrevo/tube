package org.revo.Service;

import lombok.extern.slf4j.Slf4j;
import org.revo.Config.Processor;
import org.revo.Domain.Index;
import org.revo.Domain.Master;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.MessageEndpoint;
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

    @StreamListener
    public void hls(@Input(Processor.tube_hls) Flux<Index> index) {
        log.info("receive tube_hls ");
        index.flatMap(it -> indexService.save(it)).
                flatMap(it -> masterService.append(Mono.just(it))).subscribe();
    }

    @StreamListener
    @Output(Processor.feedback_index)
    public Flux<Master> info(@Input(Processor.tube_info) Flux<Master> master) {
        log.info("receive tube_info ");
        return master.flatMap(it -> masterService.saveInfo(it));
    }

    @StreamListener
    @Output(Processor.ffmpeg_queue)
    public Flux<Master> store(@Input(Processor.tube_store) Flux<Master> master) {
        log.info("receive tube_store ");
        Flux<Master> save = master.flatMap(it -> masterService.save(it));
        log.info("send ffmpeg_queue ");
        return save;
    }
}
