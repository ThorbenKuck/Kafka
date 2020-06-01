package de.thorbenkuck.kafka;

import de.thorbenkuck.kafka.data.ExampleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class KafkaProducer {

    private final KafkaTemplate<String, String> template;
    private final KafkaProducerProperties properties;

    @Autowired
    public KafkaProducer(
            KafkaTemplate<String, String> template,
            KafkaProducerProperties properties
    ) {
        this.template = template;
        this.properties = properties;
    }

    public void send(ExampleMessage exampleMessage) {
        Message<ExampleMessage> message = MessageBuilder.withPayload(exampleMessage)
                .setHeader(KafkaHeaders.TOPIC, properties.getTopic())
                .build();

        ListenableFuture<SendResult<String, String>> send = template.send(message);
        validate(send);
    }

    private void validate(ListenableFuture<SendResult<String, String>> future) {
        // Has to be AtomicReference, because of javas approach to inner classes..
        // This is why i love Kotlin so much...
        AtomicReference<Throwable> throwableReference = new AtomicReference<>();

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                // Ignore
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwableReference.set(throwable);
            }
        });

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException(e);
        }

        if(throwableReference.get() != null) {
            // Because Java would need to propagate the exception,
            // I choose to use a plain RuntimeException. Note: this
            // is on many level considered bad practice and i would
            // highly recommend not doing this.
            //
            // Since this is for demonstration purposes only,
            // i let this one slide ;)
            throw new RuntimeException(throwableReference.get());
        }
    }
}
