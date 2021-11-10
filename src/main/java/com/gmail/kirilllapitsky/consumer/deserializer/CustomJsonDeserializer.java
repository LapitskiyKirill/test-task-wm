package com.gmail.kirilllapitsky.consumer.deserializer;

import org.springframework.kafka.support.serializer.JsonDeserializer;

public class CustomJsonDeserializer<T> extends JsonDeserializer<T> {
    public CustomJsonDeserializer() {
        super();
        this.addTrustedPackages("*");
    }
}