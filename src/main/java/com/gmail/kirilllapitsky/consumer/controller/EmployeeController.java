package com.gmail.kirilllapitsky.consumer.controller;

import com.gmail.kirilllapitsky.consumer.service.employee.EmployeeService;
import com.gmail.kirilllapitsky.consumer.dto.EmployeeDto;
import com.gmail.kirilllapitsky.consumer.dto.NewEmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public void save(
            @RequestBody NewEmployeeDto employee) throws Exception {
        employeeService.save(employee);
    }

    @GetMapping("/{employeeId}")
    public EmployeeDto getEmployee(
          @PathVariable Long employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }
}
