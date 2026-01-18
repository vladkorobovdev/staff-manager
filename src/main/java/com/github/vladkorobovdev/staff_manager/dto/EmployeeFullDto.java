package com.github.vladkorobovdev.staff_manager.dto;

import com.github.vladkorobovdev.staff_manager.enums.Role;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmployeeFullDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private BigDecimal salary;
    private String departmentName;
}
