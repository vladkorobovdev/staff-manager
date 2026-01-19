package com.github.vladkorobovdev.staff_manager.dto;

import com.github.vladkorobovdev.staff_manager.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmployeeCreateDto {
    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotBlank(message = "Department cannot be empty")
    private String departmentName;

    @NotNull(message = "Salary cannot be empty")
    @Min(value = 0, message = "Salary cannot be negative")
    private BigDecimal salary;

    @NotNull(message = "Role cannot be empty")
    private Role role;
}
