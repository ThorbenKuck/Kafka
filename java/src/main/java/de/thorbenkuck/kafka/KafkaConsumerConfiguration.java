package de.thorbenkuck.kafka;

import de.thorbenkuck.kafka.data.ExampleMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@Configuration
public class KafkaConsumerConfiguration {

    private final SmartContainerFactory factory;

    public KafkaConsumerConfiguration(SmartContainerFactory factory) {
        this.factory = factory;
    }

    @Bean("exampleMessageContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, ExampleMessage> exampleMessageContainerFactory(
            KafkaConsumerProperties properties
    ) {
        return factory.createContainerFactory(properties.getTopic(), ExampleMessage.class);
    }


}
