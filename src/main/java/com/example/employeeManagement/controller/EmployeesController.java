package com.example.employeeManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeeManagement.DTO.CreateEmployeeDto;
import com.example.employeeManagement.model.Employee;
import com.example.employeeManagement.service.impl.EmployeeServiceImp;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
    @Autowired
    EmployeeServiceImp empService;

    @PostMapping("/add/{departmentId}")
    public ResponseEntity<Employee> saveEmployee(@RequestBody @Valid CreateEmployeeDto emp) {
        return new ResponseEntity<>(empService.createEmp(emp.toEmployee()),
                HttpStatus.CREATED);
    }

    @GetMapping()
    public List<Employee> getAll() {
        return empService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable Long id) {
        return empService.getEmployee(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody CreateEmployeeDto emp, @PathVariable Long id) {
        return new ResponseEntity<>(empService.updateEmployee(id, emp.toEmployee()),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteEmp(@PathVariable Long id) {
        empService.deleteEmployee(id);
    }

}
