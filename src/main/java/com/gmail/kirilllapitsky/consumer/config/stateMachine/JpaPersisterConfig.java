package com.gmail.kirilllapitsky.consumer.config.stateMachine;

import com.gmail.kirilllapitsky.consumer.enums.Events;
import com.gmail.kirilllapitsky.consumer.enums.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

@Configuration
public class JpaPersisterConfig {

    @Bean
    public StateMachinePersister<States, Events, Long> persister(
            StateMachinePersist<States, Events, Long> defaultPersist) {
        return new DefaultStateMachinePersister<>(defaultPersist);
    }

    @Bean
    public StateMachineRuntimePersister<States, Events, Long> stateMachineRuntimePersister(
            JpaStateMachineRepository jpaStateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }
}