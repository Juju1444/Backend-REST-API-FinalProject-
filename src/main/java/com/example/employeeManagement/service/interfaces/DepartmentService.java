package com.example.employeeManagement.service.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employeeManagement.model.Department;
import com.example.employeeManagement.model.Employee;

@Service
public interface DepartmentService {
    // Get all the employees in the
    List<Department> getAllDepartments();

    Department getDepartment(Long id);

    Department createDepartment(Department department);

    Department updateDepartment(Long id, Department department);

    void deleteDepartment(Long id);
}
