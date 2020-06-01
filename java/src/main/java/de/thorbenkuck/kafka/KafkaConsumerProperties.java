package de.thorbenkuck.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kafka.input")
public class KafkaConsumerProperties {

    private String topic;

    public String getTopic() {
        return topic;
    }
}
