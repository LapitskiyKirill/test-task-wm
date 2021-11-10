package com.gmail.kirilllapitsky.consumer.util;

import com.gmail.kirilllapitsky.consumer.dto.ContractDto;
import com.gmail.kirilllapitsky.consumer.dto.EmployeeDto;
import com.gmail.kirilllapitsky.consumer.dto.NewContractDto;
import com.gmail.kirilllapitsky.consumer.dto.NewEmployeeDto;
import com.gmail.kirilllapitsky.consumer.entity.Contract;
import com.gmail.kirilllapitsky.consumer.entity.Employee;

public class Mapper {
    public static Contract mapContract(NewContractDto newContractDto) {
        return Contract.builder()
                .startDate(newContractDto.getStartDate())
                .endDate(newContractDto.getEndDate())
                .build();
    }

    public static Employee mapEmployee(NewEmployeeDto newEmployeeDto) {
        return Employee.builder()
                .firstName(newEmployeeDto.getFirstName())
                .lastName(newEmployeeDto.getLastName())
                .age(newEmployeeDto.getAge())
                .contract(mapContract(newEmployeeDto.getContract()))
                .build();
    }

    public static EmployeeDto mapEmployeeDto(Employee employee) {
        return EmployeeDto.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .age(employee.getAge())
                .state(employee.getState())
                .contract(mapContractDto(employee.getContract()))
                .build();
    }

    public static ContractDto mapContractDto(Contract contract) {
        return ContractDto.builder()
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .build();
    }
}
