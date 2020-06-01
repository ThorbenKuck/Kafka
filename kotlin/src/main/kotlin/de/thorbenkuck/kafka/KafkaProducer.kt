package de.thorbenkuck.kafka

import de.thorbenkuck.kafka.data.ExampleMessage
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.kafka.support.SendResult
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFuture

@Component
class KafkaProducer(
		private val template: KafkaTemplate<String, String>,
		private val properties: KafkaProducerProperties
) {
	fun send(exampleMessage: ExampleMessage) {
		val message = MessageBuilder.withPayload(exampleMessage)
				.setHeader(KafkaHeaders.TOPIC, properties.topic)
				.build()

		val future = template.send(message)
		validate(future)
	}

	private fun validate(future: ListenableFuture<SendResult<String, String>>) {
		var throwable: Throwable? = null

		future.addCallback(
				{},
				{ throwable = it }
		)

		future.get()
		if(throwable != null) {
			throw throwable!!
		}
	}

}