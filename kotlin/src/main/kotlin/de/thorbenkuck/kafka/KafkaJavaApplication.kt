package de.thorbenkuck.kafka

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
class KafkaJavaApplication {
	@Bean
	fun createObjectMapper(): ObjectMapper {
		return ObjectMapper().registerModules(KotlinModule())
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
	}
}

fun main(args: Array<String>) {
	runApplication<KafkaJavaApplication>(*args)
}
