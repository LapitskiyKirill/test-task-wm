package com.gmail.kirilllapitsky.consumer.test.serviceTest;

import com.gmail.kirilllapitsky.consumer.data.DataSet;
import com.gmail.kirilllapitsky.consumer.dto.NewEmployeeDto;
import com.gmail.kirilllapitsky.consumer.entity.Employee;
import com.gmail.kirilllapitsky.consumer.enums.Events;
import com.gmail.kirilllapitsky.consumer.enums.States;
import com.gmail.kirilllapitsky.consumer.exception.NoSuchEntityException;
import com.gmail.kirilllapitsky.consumer.repository.EmployeeRepository;
import com.gmail.kirilllapitsky.consumer.service.employee.EmployeeServiceImpl;
import com.gmail.kirilllapitsky.consumer.util.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.state.State;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Spy
    private EmployeeRepository employeeRepository;

    @Spy
    private StateMachine<States, Events> stateMachine;

    @Spy
    private StateMachineFactory<States, Events> stateMachineFactory;

    @Spy
    private StateMachinePersister<States, Events, Long> persister;

    @Spy
    private State<States, Events> state;

    @Spy
    private Mapper mapper;

    private Employee employee;
    private NewEmployeeDto employeeDto;
    private Employee employeeEntity;

    @BeforeEach
    public void before() {
        employeeDto = DataSet.getNewEmployeeDto();
        employee = DataSet.getEmployeeByNewEmployeeDto(employeeDto);
        employeeEntity = DataSet.getEmployeeEntity();
    }

    @Test
    public void saveNewEmployee() throws Exception {
        employee.setId(1L);
        employee.setState(States.ADDED);
        Mockito.when(stateMachineFactory.getStateMachine(employee.getId().toString())).thenReturn(stateMachine);
        Mockito.when(stateMachine.getState()).thenReturn(state);
        Mockito.when(state.getId()).thenReturn(States.ADDED);
        Mockito.mockStatic(Mapper.class).when(() -> Mapper.mapEmployee(employeeDto)).thenReturn(employee);
        Mockito.doNothing().when(persister).persist(stateMachine, employee.getId());

        employeeService.save(employeeDto);

        Mockito.verify(employeeRepository, atLeast(1)).save(employee);
    }

    @Test
    public void getEmployeeById() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employeeEntity));

        assertEquals(employeeService.getEmployeeById(1L), Mapper.mapEmployeeDto(employeeEntity));
    }

    @Test
    public void shouldFailIfNoEmployeeWithSpecifiedId() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> employeeService.getEmployeeById(1L));
    }
}
