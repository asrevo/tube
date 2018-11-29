package org.revo.Service;

import org.revo.Config.Processor;
import org.revo.Domain.Index;
import org.revo.Domain.Master;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * Created by ashraf on 23/04/17.
 */
@MessageEndpoint
public class Receiver {
    @Autowired
    private MasterService masterService;
    @Autowired
    private IndexService indexService;

    @StreamListener(value = Processor.tube_hls)
    public void hls(Message<Index> index) {
        masterService.append(indexService.save(index.getPayload()));
    }

    @StreamListener(value = Processor.tube_info)
//    @SendTo(value = Processor.indexing_queue)
    public /*Master*/void info(Message<Master> master) {
        masterService.saveInfo(master.getPayload());
        return;
    }

    @StreamListener(value = Processor.tube_store)
    @SendTo(value = Processor.ffmpeg_queue)
    public Master store(Message<Master> master) {
        return masterService.save(master.getPayload());
    }
}

//        Optional<Master> master = masterService.findOne(index.getPayload().getMaster());
//        processor.ToFeedBack_push().send(withPayload(new Stater(master.get(), Queue.TUBE_HLS, State.ON_GOING)).build());
//        processor.ToFeedBack_push().send(withPayload(new Stater(master.get(), Queue.TUBE_HLS, State.UNDER_GOING)).build());
//        processor.ToFeedBack_push().send(withPayload(new Stater(master.getPayload(), Queue.TUBE_INFO, State.ON_GOING)).build());
/*
                .ifPresent(it -> {
                    processor.ToBento4_hls().send(MessageBuilder.withPayload(it).build());
                    processor.ToFfmpeg_png().send(MessageBuilder.withPayload(it).build());

                    List<IndexImpl> collect = it.getImpls().stream().skip(1).filter(index -> index.getStatus() == Status.BINDING)
                            .collect(Collectors.toList());

                    for (int i = 0; i < collect.size(); i++) {
                        it.setImpls(Collections.singletonList(collect.get(i)));
                        processor.ToFfmpeg_mp4().send(MessageBuilder.withPayload(it)
//                                see config/src/main/resources/config/ffmpeg.yml#21
                                .setHeader("priority", 20 - i)
                                .build());

                    }
                });
*/
//        processor.ToFeedBack_push().send(withPayload(new Stater(master.getPayload(), Queue.TUBE_INFO, State.UNDER_GOING)).build());
//should group based to master.getPayload().getFile()
//        processor.ToFeedBack_push().send(withPayload(new Stater(saved, Queue.BENTO4_INFO, State.ON_GOING)).build());
//        processor.ToBento4_info().send(withPayload(saved).build());
//        processor.ToFeedBack_push().send(withPayload(new Stater(saved, Queue.BENTO4_INFO, State.UNDER_GOING)).build());
