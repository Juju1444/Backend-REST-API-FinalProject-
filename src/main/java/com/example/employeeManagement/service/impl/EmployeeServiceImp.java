package com.example.employeeManagement.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.employeeManagement.model.Employee;
import com.example.employeeManagement.repository.EmployeeRepository;
import com.example.employeeManagement.service.interfaces.EmployeeService;

@Service
public class EmployeeServiceImp implements EmployeeService {
    @Autowired
    EmployeeRepository repo;

    @Override
    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    @Override
    public Employee createEmp(Employee emp) {
        try {
            Employee empDb = repo.save(emp);
            return empDb;
        } catch (DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "this employee with " + emp.getEmail() + " allready exist");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        // return repo.save(emp);
    }

    @Override
    public Employee updateEmployee(Long id, Employee emp) {
        try {
            Employee empDb = repo.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "this employee does not exist" + id));
            // empDb.setId(id).;
            emp.setId(empDb.getId());
            repo.save(emp);
            return empDb;
        } catch (DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "this email allready exist");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }

    }

    @Override
    public Employee getEmployee(Long id) {
        Employee empDb = repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "this employee does not exist"));
        return empDb;
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee empDb = repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "this employee does not exist"));
        repo.deleteById(empDb.getId());
    }

}
