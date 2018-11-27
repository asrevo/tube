package org.revo.Config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Processor {


    String ToBento4_hls = "ToBento4_hls";

    @Output("ToBento4_hls")
    SubscribableChannel ToBento4_hls();

    String ToBento4_info = "ToBento4_info";

    @Output("ToBento4_info")
    SubscribableChannel ToBento4_info();

    String ToTube_hls = "ToTube_hls";

    @Input("ToTube_hls")
    SubscribableChannel ToTube_hls();

    String ToTube_info = "ToTube_info";

    @Input("ToTube_info")
    SubscribableChannel ToTube_info();

    String ToTube_store = "ToTube_store";

    @Input("ToTube_store")
    MessageChannel ToTube_store();


    String ToIndexing = "ToIndexing";

    @Output("ToIndexing")
    MessageChannel ToIndexing();


    String ToFfmpeg = "ToFfmpeg";

    @Output("ToFfmpeg")
    MessageChannel ToFfmpeg();


    String ToFile = "ToFile";

    @Output("ToFile")
    MessageChannel ToFile();

    String ToFeedBack_push = "ToFeedBack_push";

    @Output("ToFeedBack_push")
    MessageChannel ToFeedBack_push();


    String ToFfmpeg_mp4 = "ToFfmpeg_mp4";

    @Output("ToFfmpeg_mp4")
    MessageChannel ToFfmpeg_mp4();

    String ToFfmpeg_png = "ToFfmpeg_png";

    @Output("ToFfmpeg_png")
    MessageChannel ToFfmpeg_png();
}
