package org.revo.Config;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;

@Configuration
public class Util {

    @Bean
    public LoggingEventListener loggingEventListener() {
        return new LoggingEventListener();
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSqs) {
        return new QueueMessagingTemplate(amazonSqs);
    }
}
