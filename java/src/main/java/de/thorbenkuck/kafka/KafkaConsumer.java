package de.thorbenkuck.kafka;

import de.thorbenkuck.kafka.data.ExampleMessage;
import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumer {

    @KafkaListener(topics = "${kafka.input.topic}", containerFactory = "exampleMessageContainerFactor")
    public void listen(ExampleMessage exampleMessage) {
        System.out.println("We received: " + exampleMessage.getMessage());
    }

}
