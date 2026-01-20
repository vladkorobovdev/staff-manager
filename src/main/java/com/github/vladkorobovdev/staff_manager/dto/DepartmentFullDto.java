package com.github.vladkorobovdev.staff_manager.dto;

import lombok.Data;

import java.util.List;

@Data
public class DepartmentFullDto extends DepartmentDto{
    private List<EmployeeShortDto> employees;
}
