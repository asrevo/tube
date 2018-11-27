package org.revo.Service;

import lombok.extern.slf4j.Slf4j;
import org.revo.Config.Processor;
import org.revo.Domain.Index;
import org.revo.Domain.Master;
import org.revo.Domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.messaging.support.MessageBuilder.withPayload;

/**
 * Created by ashraf on 23/04/17.
 */
@MessageEndpoint
@Slf4j
public class Receiver {
    @Autowired
    private Processor processor;
    @Autowired
    private MasterService masterService;
    @Autowired
    private IndexService indexService;

    @StreamListener(value = Processor.ToTube_hls)
    public void hls(Message<Index> index) {
        log.info("recived " + index.getPayload().getId());

//        Optional<Master> master = masterService.findOne(index.getPayload().getMaster());
//        processor.ToFeedBack_push().send(withPayload(new Stater(master.get(), Queue.TUBE_HLS, State.ON_GOING)).build());
        masterService.append(indexService.save(index.getPayload()));
//        processor.ToFeedBack_push().send(withPayload(new Stater(master.get(), Queue.TUBE_HLS, State.UNDER_GOING)).build());
    }

    @StreamListener(value = Processor.ToTube_info)
    public void info(Message<Master> master) {
        log.info("recived " + master.getPayload().getId());
//        processor.ToFeedBack_push().send(withPayload(new Stater(master.getPayload(), Queue.TUBE_INFO, State.ON_GOING)).build());
        masterService.update(master.getPayload())
                .ifPresent(it -> {
                    processor.ToBento4_hls().send(MessageBuilder.withPayload(it).build());
                    processor.ToFfmpeg_png().send(MessageBuilder.withPayload(it).build());
                    it.getImpls().stream().skip(1).filter(index -> index.getStatus() == Status.BINDING).forEach(index -> {
                        it.setImpls(Collections.singletonList(index));
                        processor.ToFfmpeg_mp4().send(MessageBuilder.withPayload(it)
                                .build());
                    });
                });
//        processor.ToFeedBack_push().send(withPayload(new Stater(master.getPayload(), Queue.TUBE_INFO, State.UNDER_GOING)).build());
    }

    @StreamListener(value = Processor.ToTube_store)
    public void store(Message<Master> master) {
        log.info("recived " + master.getPayload().getId());
        Master saved = masterService.save(master.getPayload());
//should group based to master.getPayload().getFile()
//        processor.ToFeedBack_push().send(withPayload(new Stater(saved, Queue.BENTO4_INFO, State.ON_GOING)).build());
        processor.ToBento4_info().send(withPayload(saved).build());
//        processor.ToFeedBack_push().send(withPayload(new Stater(saved, Queue.BENTO4_INFO, State.UNDER_GOING)).build());
    }
}