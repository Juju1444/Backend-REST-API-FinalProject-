package com.example.employeeManagement.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
// import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Getter
public class Employee extends User {
    @Column(nullable = false)
    private Long empNo;
    private double salary;
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;



}


// @Builder
// public Employee(Long id, String firstName, String lastName, String email,
// String password, Role role,
// LocalDateTime createdAt, LocalDateTime updatedAt, Long empNo, double salary,
// Department department) {
// super(id, firstName, lastName, email, password, role, createdAt, updatedAt);
// this.empNo = empNo;
// this.salary = salary;
// this.department = department;
// }