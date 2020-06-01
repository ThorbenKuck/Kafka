package de.thorbenkuck.kafka;

import de.thorbenkuck.kafka.data.ExampleMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "${kafka.input.topic}", containerFactory = "exampleMessageContainerFactory")
    public void listen(ExampleMessage exampleMessage) {
        System.out.println("We received: " + exampleMessage.getMessage());
    }

}
