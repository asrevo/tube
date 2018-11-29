package org.revo.Config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Processor {
    String tube_hls = "tube_hls";

    @Input("tube_hls")
    SubscribableChannel tube_hls();

    String tube_info = "tube_info";

    @Input("tube_info")
    SubscribableChannel tube_info();

    String tube_store = "tube_store";

    @Input("tube_store")
    MessageChannel tube_store();


    String indexing_queue = "indexing_queue";

    @Output("indexing_queue")
    MessageChannel indexing_queue();


    String ffmpeg_queue = "ffmpeg_queue";

    @Output("ffmpeg_queue")
    MessageChannel ffmpeg_queue();


    String file_queue = "file_queue";

    @Output("file_queue")
    MessageChannel file_queue();

    String feedBack_push = "feedBack_push";

    @Output("feedBack_push")
    MessageChannel feedBack_push();
}
