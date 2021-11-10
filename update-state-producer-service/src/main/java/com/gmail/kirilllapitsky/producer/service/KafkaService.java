package com.gmail.kirilllapitsky.producer.service;

public interface KafkaService {
    void sendMessage(Long employeeId);
}
