package com.github.vladkorobovdev.staff_manager.service;

import com.github.vladkorobovdev.staff_manager.dto.EmployeeFullDto;
import com.github.vladkorobovdev.staff_manager.entity.Department;
import com.github.vladkorobovdev.staff_manager.entity.Employee;
import com.github.vladkorobovdev.staff_manager.enums.Role;
import com.github.vladkorobovdev.staff_manager.mapper.EmployeeMapper;
import com.github.vladkorobovdev.staff_manager.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmplyeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeMapper employeeMapper;
    @Mock
    private DepartmentService departmentService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    @DisplayName("Create Employee: Success")
    void createEmployee_ShouldSaveAndReturnDto() {
        String email = "test@mail.com";
        String rawPassword = "password123";
        String encodedPassword = "encoded_password_hash";
        String deptName = "IT";

        Department department = new Department();
        department.setName(deptName);

        Employee savedEmployee = new Employee();
        savedEmployee.setId(1L);
        savedEmployee.setEmail(email);
        savedEmployee.setPassword(encodedPassword);
        savedEmployee.setDepartment(department);

        EmployeeFullDto expectedDto = new EmployeeFullDto();
        expectedDto.setId(1L);
        expectedDto.setEmail(email);
        expectedDto.setDepartmentName(deptName);

        when(departmentService.getByName(deptName)).thenReturn(department);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);
        when(employeeMapper.toFullDto(savedEmployee)).thenReturn(expectedDto);

        EmployeeFullDto result = employeeService.createEmployee(
                "John", "Doe", email, rawPassword, deptName, BigDecimal.valueOf(1000), Role.USER
        );

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(deptName, result.getDepartmentName());

        verify(passwordEncoder, times(1)).encode(rawPassword);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }
}
