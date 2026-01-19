package com.github.vladkorobovdev.staff_manager.service;

import com.github.vladkorobovdev.staff_manager.dto.EmployeeFullDto;
import com.github.vladkorobovdev.staff_manager.dto.EmployeeShortDto;
import com.github.vladkorobovdev.staff_manager.entity.Department;
import com.github.vladkorobovdev.staff_manager.entity.Employee;
import com.github.vladkorobovdev.staff_manager.enums.Role;
import com.github.vladkorobovdev.staff_manager.mapper.EmployeeMapper;
import com.github.vladkorobovdev.staff_manager.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final DepartmentService departmentService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public EmployeeFullDto createEmployee(
            String firstName,
            String lastName,
            String email,
            String password,
            String departmentName,
            BigDecimal salary,
            Role role
    ) {
        Department department = departmentService.getByName(departmentName);

        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setSalary(salary);
        employee.setRole(role);
        employee.setDepartment(department);
        employee.setPassword(passwordEncoder.encode(password));

        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toFullDto(saved);
    }

    public List<EmployeeShortDto> getAll() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toShortDto)
                .toList();
    }
}