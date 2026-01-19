package com.github.vladkorobovdev.staff_manager.controller;

import com.github.vladkorobovdev.staff_manager.dto.DepartmentDto;
import com.github.vladkorobovdev.staff_manager.entity.Department;
import com.github.vladkorobovdev.staff_manager.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public List<DepartmentDto> getAll() {
        return departmentService.getAll();
    }

    @PostMapping
    public DepartmentDto create(@RequestParam String name) {
        return departmentService.createDepartment(name);
    }
}
