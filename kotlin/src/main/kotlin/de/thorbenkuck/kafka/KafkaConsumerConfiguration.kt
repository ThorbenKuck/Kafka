package de.thorbenkuck.kafka

import de.thorbenkuck.kafka.data.ExampleMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory

@Configuration
class KafkaConsumerConfiguration(
		private val factory: SmartContainerFactory
) {

	@Bean("exampleMessageContainerFactory")
	fun exampleMessageContainerFactory(
			properties: KafkaConsumerProperties
	): ConcurrentKafkaListenerContainerFactory<String, ExampleMessage> = factory.createContainerFactory(properties.topic)

}