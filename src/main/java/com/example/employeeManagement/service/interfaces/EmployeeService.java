package com.example.employeeManagement.service.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employeeManagement.model.Employee;

@Service
public interface EmployeeService {
    // Get all the employees in the
    List<Employee> getAllEmployees();

    Employee getEmployee(Long id);

    Employee createEmp(Employee emp);

    Employee updateEmployee(Long id, Employee emp);

    void deleteEmployee(Long id);
}
