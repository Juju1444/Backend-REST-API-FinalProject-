package com.example.employeeManagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.employeeManagement.model.Department;
import com.example.employeeManagement.repository.DepartmentRepository;
import com.example.employeeManagement.service.interfaces.DepartmentService;

@Service
public class DepaertmentImpl implements DepartmentService {
    @Autowired
    DepartmentRepository repo;

    @Override
    public List<Department> getAllDepartments() {
        return repo.findAll();
    }

    @Override
    public Department getDepartment(Long id) {
        Department empDb = repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "this employee does not exist"));
        return empDb;
    }

    @Override
    public Department createDepartment(Department department) {
        try {
            Department deptDb = repo.save(department);
            return deptDb;
        } catch (DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "this employee with " + department.getDeptName() + " allready exist");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    // return repo.save(emp); }

    @Override
    public Department updateDepartment(Long id, Department department) {
        try {
            Department deptDb = repo.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "this employee does not exist"));
            // empDb.setId(id).;
            department.setId(deptDb.getId());
            repo.save(department);
            return deptDb;
        } catch (DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "this department allready exist");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @Override
    public void deleteDepartment(Long id) {
        Department deptDb = repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "this Department does not exist"));
        repo.deleteById(deptDb.getId());
    }

}
