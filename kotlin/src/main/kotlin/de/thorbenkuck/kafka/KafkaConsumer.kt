package de.thorbenkuck.kafka

import de.thorbenkuck.kafka.data.ExampleMessage
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.KafkaListener

@Configuration
class KafkaConsumer {

	@KafkaListener(
			topics = ["\${kafka.input.topic}"],
			containerFactory = "exampleMessageContainerFactor"
	)
	fun listen(exampleMessage: ExampleMessage) {
		println("We received: " + exampleMessage.message)
	}
}