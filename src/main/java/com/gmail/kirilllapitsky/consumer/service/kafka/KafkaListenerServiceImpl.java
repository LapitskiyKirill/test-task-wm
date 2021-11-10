package com.gmail.kirilllapitsky.consumer.service.kafka;

import com.gmail.kirilllapitsky.consumer.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KafkaListenerServiceImpl implements KafkaListenerService {
    private final EmployeeService employeeService;

    @Override
    @KafkaListener(topics = "changeState", groupId = "project")
    public void listen(Long employeeId) throws Exception {
        employeeService.processState(employeeId);
    }
}
