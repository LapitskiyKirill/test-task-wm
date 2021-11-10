package com.gmail.kirilllapitsky.producer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KafkaServiceImpl implements KafkaService {
    private final KafkaTemplate<String, Long> kafkaTemplate;
    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    @Override
    public void sendMessage(Long employeeId) {
        ListenableFuture<SendResult<String, Long>> future =
                kafkaTemplate.send(topic, employeeId);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, Long> result) {
                System.out.println("Sent message with employee id=" + employeeId);
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message with employee id=" + employeeId + " due to : " + ex.getMessage());
            }
        });
    }
}
