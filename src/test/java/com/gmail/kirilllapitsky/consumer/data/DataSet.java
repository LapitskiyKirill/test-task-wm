package com.gmail.kirilllapitsky.consumer.data;

import com.gmail.kirilllapitsky.consumer.dto.NewContractDto;
import com.gmail.kirilllapitsky.consumer.dto.NewEmployeeDto;
import com.gmail.kirilllapitsky.consumer.entity.Contract;
import com.gmail.kirilllapitsky.consumer.entity.Employee;
import com.gmail.kirilllapitsky.consumer.enums.States;

import java.time.LocalDate;

import static com.gmail.kirilllapitsky.consumer.util.Mapper.mapContract;

public class DataSet {
    public static NewEmployeeDto getNewEmployeeDto() {
        return NewEmployeeDto.builder()
                .firstName("firstName")
                .lastName("lastName")
                .age(25)
                .contract(
                        NewContractDto.builder()
                                .startDate(LocalDate.of(2021, 10, 1))
                                .endDate(LocalDate.of(2022, 10, 1))
                                .build()
                ).build();
    }

    public static Employee getEmployeeByNewEmployeeDto(NewEmployeeDto employeeDto) {
        return Employee.builder()
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .age(employeeDto.getAge())
                .contract(mapContract(employeeDto.getContract()))
                .build();
    }

    public static Employee getEmployeeEntity() {
        return Employee.builder()
                .id(1L)
                .firstName("firstName")
                .lastName("lastName")
                .age(25)
                .state(States.ADDED)
                .contract(
                        Contract.builder()
                                .id(1L)
                                .startDate(LocalDate.of(2021, 10, 1))
                                .endDate(LocalDate.of(2022, 10, 1))
                                .build()
                ).build();
    }
}
