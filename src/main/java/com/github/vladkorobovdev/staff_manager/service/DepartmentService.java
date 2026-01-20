package com.github.vladkorobovdev.staff_manager.service;

import com.github.vladkorobovdev.staff_manager.dto.DepartmentDto;
import com.github.vladkorobovdev.staff_manager.dto.DepartmentFullDto;
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

    public DepartmentFullDto getDepartmentWithEmployees(Long id) {
        Department department = departmentRepository.findByIdWithEmployees(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        return departmentMapper.toDepartmentFullDto(department);
    }

    @Transactional
    public DepartmentDto update(
            Long id,
            String name
    ) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department with id " + id + " was not found"));

        if (!department.getName().equals(name) && departmentRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Department with name '" + name + "' already exists");
        }

        department.setName(name);

        departmentRepository.save(department);

        return departmentMapper.toDepartmentDto(department);
    }

    @Transactional
    public void delete(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department with id " + id + " was not found"));

        if (!department.getEmployees().isEmpty()) {
            throw new RuntimeException("Cannot delete department with existing employees. Please move them first.");
        }

        departmentRepository.delete(department);
    }
}
