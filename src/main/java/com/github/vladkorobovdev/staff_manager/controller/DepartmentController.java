package com.github.vladkorobovdev.staff_manager.controller;

import com.github.vladkorobovdev.staff_manager.dto.DepartmentDto;
import com.github.vladkorobovdev.staff_manager.dto.DepartmentFullDto;
import com.github.vladkorobovdev.staff_manager.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<DepartmentDto> getAll() {
        return departmentService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public DepartmentFullDto getById(@PathVariable Long id) {
        return departmentService.getDepartmentWithEmployees(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public DepartmentDto create(@RequestParam String name) {
        return departmentService.createDepartment(name);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public DepartmentDto update(
            @RequestParam Long id,
            @RequestParam String name
    ) {
        return departmentService.update(id, name);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable Long id) {
        departmentService.delete(id);
    }
}
