package de.thorbenkuck.kafka

import de.thorbenkuck.kafka.data.ExampleMessage
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer {

	@KafkaListener(
			topics = ["\${kafka.input.topic}"],
			containerFactory = "exampleMessageContainerFactory"
	)
	fun listen(exampleMessage: ExampleMessage) {
		println("We received: " + exampleMessage.message)
	}
}