package de.thorbenkuck.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.SeekToCurrentErrorHandler
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.FailedDeserializationInfo
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.stereotype.Component

// TODO Please do not use this name...
@Component
class SmartContainerFactory(
		val objectMapper: ObjectMapper,
		val kafkaProperties: KafkaProperties
) {

	final inline fun <reified T> createContainerFactory(
			topic: String
	): ConcurrentKafkaListenerContainerFactory<String, T> {
		val base: JsonDeserializer<T> = JsonDeserializer<T>(T::class.java, objectMapper)
		base.setUseTypeHeaders(false)
		base.addTrustedPackages("*")

		val deserializer = ErrorHandlingDeserializer(base)
		deserializer.setFailedDeserializationFunction {
			System.err.println("Error deserializing data from topic ${it.topic}")
			null
		}

		val consumerProperties = kafkaProperties.buildConsumerProperties()
		consumerProperties[ConsumerConfig.GROUP_ID_CONFIG] = consumerProperties[ConsumerConfig.GROUP_ID_CONFIG].toString() + ".$topic"

		val consumerFactory = DefaultKafkaConsumerFactory(
				consumerProperties,
				StringDeserializer(),
				deserializer
		)
		val factory = ConcurrentKafkaListenerContainerFactory<String, T>()
		factory.consumerFactory = consumerFactory
		factory.setErrorHandler(SeekToCurrentErrorHandler())
		return factory
	}
}