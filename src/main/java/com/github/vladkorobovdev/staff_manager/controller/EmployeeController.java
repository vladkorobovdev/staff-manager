package com.github.vladkorobovdev.staff_manager.controller;

import com.github.vladkorobovdev.staff_manager.dto.EmployeeCreateDto;
import com.github.vladkorobovdev.staff_manager.dto.EmployeeFullDto;
import com.github.vladkorobovdev.staff_manager.dto.EmployeeShortDto;
import com.github.vladkorobovdev.staff_manager.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<EmployeeShortDto> getAll() {
        return employeeService.getAll();
    }

    @PostMapping
    public EmployeeFullDto create(@RequestBody @Valid EmployeeCreateDto dto) {
        return employeeService.createEmployee(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getDepartmentName(),
                dto.getSalary()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public EmployeeFullDto update(
            @PathVariable Long id,
            @RequestBody @Valid EmployeeFullDto dto
    ) {
        return employeeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(
            @PathVariable Long id
    ) {
        employeeService.delete(id);
    }
}
