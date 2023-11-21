package com.example.employeeManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.employeeManagement.DTO.CreateDepartmentDto;
import com.example.employeeManagement.model.Department;
import com.example.employeeManagement.service.impl.DepaertmentImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/depts")
public class DepartmentController {
    @Autowired
    DepaertmentImpl deptService;

    @GetMapping("/add")
    public List<Department> getAll() {
        return deptService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getOne(@PathVariable Long id) {
        return deptService.getDepartment(id);
    }

    @PostMapping()
    public ResponseEntity<Department> saveDept(@RequestBody @Valid CreateDepartmentDto dept) {
        return new ResponseEntity<>(deptService.createDepartment(dept.toDepartment()),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDept(@RequestBody @Valid CreateDepartmentDto dept, @PathVariable Long id) {
        return new ResponseEntity<>(deptService.updateDepartment(id, dept.toDepartment()),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable Long id) {
        deptService.deleteDepartment(id);
    }

}
