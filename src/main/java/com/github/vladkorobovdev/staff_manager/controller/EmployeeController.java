package com.github.vladkorobovdev.staff_manager.controller;

import com.github.vladkorobovdev.staff_manager.dto.EmployeeCreateDto;
import com.github.vladkorobovdev.staff_manager.dto.EmployeeFullDto;
import com.github.vladkorobovdev.staff_manager.dto.EmployeeShortDto;
import com.github.vladkorobovdev.staff_manager.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeShortDto> getAll() {
        return employeeService.getAll();
    }

    @PostMapping
    public EmployeeFullDto create(@RequestBody EmployeeCreateDto dto) {
        return employeeService.createEmployee(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getDepartmentName(),
                dto.getSalary(),
                dto.getRole()
        );
    }
}
