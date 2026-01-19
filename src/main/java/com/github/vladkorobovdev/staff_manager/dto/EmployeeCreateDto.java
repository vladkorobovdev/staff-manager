package com.github.vladkorobovdev.staff_manager.dto;

import com.github.vladkorobovdev.staff_manager.enums.Role;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmployeeCreateDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String departmentName;
    private BigDecimal salary;
    private Role role;
}
