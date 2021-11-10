package com.gmail.kirilllapitsky.consumer.service.employee;

import com.gmail.kirilllapitsky.consumer.dto.EmployeeDto;
import com.gmail.kirilllapitsky.consumer.dto.NewEmployeeDto;
import com.gmail.kirilllapitsky.consumer.entity.Employee;
import com.gmail.kirilllapitsky.consumer.enums.Events;
import com.gmail.kirilllapitsky.consumer.enums.States;
import com.gmail.kirilllapitsky.consumer.exception.NoSuchEntityException;
import com.gmail.kirilllapitsky.consumer.repository.EmployeeRepository;
import com.gmail.kirilllapitsky.consumer.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final StateMachineFactory<States, Events> stateMachineFactory;
    private final StateMachinePersister<States, Events, Long> persister;
    private final Map<States, Events> events;

    @Override
    public void save(NewEmployeeDto newEmployeeDto) throws Exception {
        Employee employee = Mapper.mapEmployee(newEmployeeDto);
        employeeRepository.save(employee);
        StateMachine<States, Events> stateMachine = stateMachineFactory.getStateMachine(employee.getId().toString());
        employee.setState(stateMachine.getState().getId());
        persister.persist(stateMachine, employee.getId());
        employeeRepository.save(employee);
    }

    @Override
    public void processState(Long employeeId) throws Exception {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new NoSuchEntityException(String.format("No employee with id = %d", employeeId))
        );
        StateMachine<States, Events> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine = persister.restore(stateMachine, employeeId);

        Mono<Message<Events>> mono = Mono.just(MessageBuilder.withPayload(events.get(stateMachine.getState().getId())).build());
        stateMachine.sendEvent(mono)
                .subscribe(res -> log.debug(res.toString()));

        employee.setState(stateMachine.getState().getId());
        employeeRepository.save(employee);
        persister.persist(stateMachine, employee.getId());
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new NoSuchEntityException(String.format("No employee with id = %d", employeeId))
        );
        return Mapper.mapEmployeeDto(employee);
    }
}