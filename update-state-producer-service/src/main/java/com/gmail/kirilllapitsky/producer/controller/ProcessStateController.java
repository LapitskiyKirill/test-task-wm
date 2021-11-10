package com.gmail.kirilllapitsky.producer.controller;

import com.gmail.kirilllapitsky.producer.service.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/state")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProcessStateController {
    private final KafkaService kafkaService;

    @PostMapping("/{employeeId}")
    public void processEmployeeState(
            @PathVariable Long employeeId) {
        kafkaService.sendMessage(employeeId);
    }
}
