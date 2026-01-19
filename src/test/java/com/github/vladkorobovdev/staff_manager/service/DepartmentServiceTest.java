package com.github.vladkorobovdev.staff_manager.service;

import com.github.vladkorobovdev.staff_manager.dto.DepartmentDto;
import com.github.vladkorobovdev.staff_manager.entity.Department;
import com.github.vladkorobovdev.staff_manager.mapper.DepartmentMapper;
import com.github.vladkorobovdev.staff_manager.repository.DepartmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    @DisplayName("Create Department: Success")
    void createDepartment_ShouldReturnDto() {
        String departmentName = "IT";
        Department department = new Department();
        department.setId(1L);
        department.setName(departmentName);

        DepartmentDto expectedDto = new DepartmentDto();
        expectedDto.setId(1L);
        expectedDto.setName(departmentName);

        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        when(departmentMapper.toDepartmentDto(department)).thenReturn(expectedDto);

        DepartmentDto result = departmentService.createDepartment(departmentName);

        assertNotNull(result);
        assertEquals(departmentName, result.getName());
        assertEquals(1L, result.getId());

        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    @DisplayName("Get By Name Not Found -> Exception")
    void getByName_ShouldThrowException_WhenNotFound() {
        String name = "Unknown";

        when(departmentRepository.findByName(name)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            departmentService.getByName(name);
        });

        assertTrue(exception.getMessage().contains("was not found"));
    }
}
