package com.gmail.kirilllapitsky.consumer.test.stateMachineTest;

import com.gmail.kirilllapitsky.consumer.data.DataSet;
import com.gmail.kirilllapitsky.consumer.entity.Employee;
import com.gmail.kirilllapitsky.consumer.enums.Events;
import com.gmail.kirilllapitsky.consumer.enums.States;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StateMachineTest {
    @Autowired
    private StateMachineFactory<States, Events> stateMachineFactory;
    private Employee employee;

    @BeforeEach
    public void before() {
        employee = DataSet.getEmployeeEntity();
    }

    @Test
    public void shouldCreateStateMachineTest() {
        StateMachine<States, Events> stateMachine = stateMachineFactory.getStateMachine(employee.getId().toString());
        assertEquals(stateMachine.getState().getId(), States.ADDED);
    }

    @Test
    public void shouldSetNextStateTest() {
        StateMachine<States, Events> stateMachine = stateMachineFactory.getStateMachine(employee.getId().toString());
        Mono<Message<Events>> mono = Mono.just(MessageBuilder.withPayload(Events.TO_IN_CHECK).build());
        stateMachine.sendEvent(mono)
                .subscribe(res -> log.debug(res.toString()));
        assertEquals(stateMachine.getState().getId(), States.IN_CHECK);
    }
}
