package com.gmail.kirilllapitsky.consumer.repository;

import com.gmail.kirilllapitsky.consumer.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
