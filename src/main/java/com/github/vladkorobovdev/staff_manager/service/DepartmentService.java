package com.github.vladkorobovdev.staff_manager.service;

import com.github.vladkorobovdev.staff_manager.dto.DepartmentDto;
import com.github.vladkorobovdev.staff_manager.entity.Department;
import com.github.vladkorobovdev.staff_manager.mapper.DepartmentMapper;
import com.github.vladkorobovdev.staff_manager.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Transactional
    public DepartmentDto createDepartment(String name) {
        Department department = new Department();
        department.setName(name);

        Department saved = departmentRepository.save(department);

        return departmentMapper.toDepartmentDto(saved);
    }

    public Department getByName(String name) {
        return departmentRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Department " + name + " was not found"));
    }

    public List<DepartmentDto> getAll() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toDepartmentDto)
                .toList();
    }
}
