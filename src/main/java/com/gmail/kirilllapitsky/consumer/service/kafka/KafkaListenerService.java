package com.gmail.kirilllapitsky.consumer.service.kafka;

public interface KafkaListenerService {
    void listen(Long employeeId) throws Exception;
}
