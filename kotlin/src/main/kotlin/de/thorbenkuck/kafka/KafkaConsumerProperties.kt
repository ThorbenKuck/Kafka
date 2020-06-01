package de.thorbenkuck.kafka

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "kafka.input")
class KafkaConsumerProperties {
	lateinit var topic: String
}