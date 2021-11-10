package com.gmail.kirilllapitsky.consumer.config.stateMachine;

import com.gmail.kirilllapitsky.consumer.enums.Events;
import com.gmail.kirilllapitsky.consumer.enums.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableStateMachineFactory
class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {
    @Autowired
    private StateMachineRuntimePersister<States, Events, Long> stateMachineRuntimePersister;

    @Override
    public void configure(final StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.withPersistence()
                .runtimePersister(stateMachineRuntimePersister);
        config.withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.ADDED)
                .end(States.ACTIVE)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.ADDED).target(States.IN_CHECK)
                .event(Events.TO_IN_CHECK)
                .and()
                .withExternal()
                .source(States.IN_CHECK).target(States.APPROVED)
                .event(Events.TO_APPROVED)
                .and()
                .withExternal()
                .source(States.APPROVED).target(States.ACTIVE)
                .event(Events.TO_ACTIVE);
    }

    @Bean
    public Map<States, Events> events() {
        Map<States, Events> events = new HashMap<>();
        events.put(States.ADDED, Events.TO_IN_CHECK);
        events.put(States.IN_CHECK, Events.TO_APPROVED);
        events.put(States.APPROVED, Events.TO_ACTIVE);
        return events;
    }
}