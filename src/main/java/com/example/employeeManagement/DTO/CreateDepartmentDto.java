package com.example.employeeManagement.DTO;

import com.example.employeeManagement.model.Department;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateDepartmentDto {
    @NotNull(message = "name is required")
    @NotEmpty(message = "name must not be empty")
    private String name;

    @NotNull(message = "phone is required")
    @NotEmpty(message = "phone must not be empty")
    private String phone;

    public Department toDepartment() {
        Department dept = new Department();
        dept.setDeptName(name);
        dept.setDeptPhone(phone);
        return dept;
    }
}
