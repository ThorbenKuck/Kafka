package de.thorbenkuck.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.util.Map;

// TODO Please do not use this name...
@Component
public class SmartContainerFactory {

    private final ObjectMapper objectMapper;
    private final KafkaProperties kafkaProperties;

    public SmartContainerFactory(
            ObjectMapper objectMapper,
            KafkaProperties kafkaProperties
    ) {
        this.objectMapper = objectMapper;
        this.kafkaProperties = kafkaProperties;
    }

    public <T> ConcurrentKafkaListenerContainerFactory<String, T> createContainerFactory(
            String topic,
            Class<T> type
    ) {
        JsonDeserializer<T> base = new JsonDeserializer<>(type, objectMapper);
        base.setUseTypeHeaders(false);
        base.addTrustedPackages("*");

        ErrorHandlingDeserializer<T> deserializer = new ErrorHandlingDeserializer<>(base);
        deserializer.setFailedDeserializationFunction(o -> {
            System.err.println("Error deserializing data from topic " + o.getTopic());
            return null;
        });

        Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerProperties.get(ConsumerConfig.GROUP_ID_CONFIG) + "." + topic);

        DefaultKafkaConsumerFactory<String, T> consumerFactory = new DefaultKafkaConsumerFactory<>(
                consumerProperties,
                new StringDeserializer(),
                deserializer
        );


        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setErrorHandler(new SeekToCurrentErrorHandler());
        return factory;
    }
}
