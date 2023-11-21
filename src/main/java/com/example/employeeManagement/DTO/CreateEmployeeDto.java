package com.example.employeeManagement.DTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.employeeManagement.model.Department;
import com.example.employeeManagement.model.Employee;
import com.example.employeeManagement.model.Role;
import com.example.employeeManagement.repository.DepartmentRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
// @JsonSerialize
public class CreateEmployeeDto {
    // @JsonIgnore
    // @Autowired
    private final DepartmentRepository deptRepo;

    @NotBlank(message = "the first name must not be empty")
    private String firstName;
    @NotBlank(message = "the last name must not be empty")
    private String lastName;

    @NotNull(message = "empolyee number is required")
    private Long empNo;

    @NotBlank(message = "the email must not be empty")
    @Email(message = "this email is invalid")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 6, max = 16, message = "the length of the password is invalid")
    private String password;

    @NotNull(message = "salary must not be empty")
    private double salary;

    @NotNull(message = "dept is required")
    private Long dept;

    public Employee toEmployee() {
        Employee emp = new Employee();
        emp.setEmail(email);
        emp.setFirstName(firstName);
        emp.setLastName(lastName);
        emp.setPassword(password);
        emp.setSalary(salary);
        emp.setEmpNo(empNo);
        emp.setRole(Role.ROLE_ADMIN);
        emp.setDepartment(Department.builder().id(dept).build());
        // if (this.deptRepo != null)
        // emp.setDepartment(
        // deptRepo.findById(dept).orElse(
        // new Department(1L, "HR", "00966535598878", null)));
        // else
        // emp.setDepartment(

        // new Department(1L, "HR", "00966535598878", null));

        return emp;
    }

}
