package de.thorbenkuck.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaProducerConfiguration {
	@Bean
	fun producerFactory(
			kafkaProperties: KafkaProperties,
			objectMapper: ObjectMapper?
	): ProducerFactory<String, String> = DefaultKafkaProducerFactory(
			kafkaProperties.buildProducerProperties(),
			StringSerializer(),
			JsonSerializer(objectMapper)
	)

	@Bean
	fun kafkaTemplate(factory: ProducerFactory<String, String>) = KafkaTemplate(factory)
}