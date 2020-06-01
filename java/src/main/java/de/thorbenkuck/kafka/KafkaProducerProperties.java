package de.thorbenkuck.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kafka.output")
public class KafkaProducerProperties {

    private String topic;

    public String getTopic() {
        return topic;
    }
}
