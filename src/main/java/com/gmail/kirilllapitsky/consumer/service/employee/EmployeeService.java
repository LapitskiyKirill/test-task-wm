package com.gmail.kirilllapitsky.consumer.service.employee;

import com.gmail.kirilllapitsky.consumer.dto.EmployeeDto;
import com.gmail.kirilllapitsky.consumer.dto.NewEmployeeDto;

public interface EmployeeService {
    void save(NewEmployeeDto newEmployeeDto) throws Exception;

    void processState(Long employeeId) throws Exception;

    EmployeeDto getEmployeeById(Long employeeId);
}
