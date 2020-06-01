package de.thorbenkuck.kafka.data;

public class ExampleMessage {

    private final String message;

    public ExampleMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
