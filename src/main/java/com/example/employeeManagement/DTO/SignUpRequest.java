package com.example.employeeManagement.DTO;

import com.example.employeeManagement.model.Role;
import com.example.employeeManagement.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
  @NotBlank(message = "the first name must not be empty")
  private String firstName;
  @NotBlank(message = "the last name must not be empty")
  private String lastName;

  @NotBlank(message = "the email must not be empty")
  @Email(message = "this email is invalid")
  private String email;
  @NotBlank(message = "password is required")
  @Size(min = 6, max = 16, message = "the length of the password is invalid")
  private String password;

  public User toUser() {
    User user = new User();
    user.setEmail(email);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setPassword(password);
    user.setRole(Role.ROLE_ADMIN);
    return user;
  }
}
