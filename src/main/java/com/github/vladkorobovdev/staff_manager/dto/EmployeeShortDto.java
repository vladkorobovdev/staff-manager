package com.github.vladkorobovdev.staff_manager.dto;

import com.github.vladkorobovdev.staff_manager.enums.Role;
import lombok.Data;

@Data
public class EmployeeShortDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String departmentName;
}
