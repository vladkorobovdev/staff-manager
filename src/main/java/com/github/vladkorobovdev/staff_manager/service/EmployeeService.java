package com.github.vladkorobovdev.staff_manager.service;

import com.github.vladkorobovdev.staff_manager.dto.ChangePasswordDto;
import com.github.vladkorobovdev.staff_manager.dto.EmployeeFullDto;
import com.github.vladkorobovdev.staff_manager.dto.EmployeeShortDto;
import com.github.vladkorobovdev.staff_manager.dto.UserProfileUpdateDto;
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
            BigDecimal salary
    ) {
        Department department = departmentService.getByName(departmentName);

        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setSalary(salary);
        employee.setRole(Role.USER);
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

    @Transactional
    public EmployeeFullDto update(Long id, EmployeeFullDto dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " was not found."));

        if (!employee.getEmail().equals(dto.getEmail())
                && employeeRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email '" + dto.getEmail() + "' is already in use.");
        }

        Department department = departmentService.getByName(dto.getDepartmentName());

        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(department);
        employee.setSalary(dto.getSalary());
        employee.setRole(dto.getRole());

        employeeRepository.save(employee);

        return employeeMapper.toFullDto(employee);
    }

    @Transactional
    public void delete(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User was not found"));

        employeeRepository.delete(employee);
    }

    @Transactional
    public EmployeeShortDto updateProfile(String currentUserEmail, UserProfileUpdateDto dto) {
        Employee employee = employeeRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("Current user not found"));


        if (dto.getFirstName() != null && !dto.getFirstName().isBlank()) {
            employee.setFirstName(dto.getFirstName());
        }

        if (dto.getLastName() != null && !dto.getLastName().isBlank()) {
            employee.setLastName(dto.getLastName());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            if (!employee.getEmail().equals(dto.getEmail())
                    && employeeRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new RuntimeException("Email " + dto.getEmail() + " is already in use");
            }
            employee.setEmail(dto.getEmail());
        }

        return employeeMapper.toShortDto(employeeRepository.save(employee));
    }

    @Transactional
    public void changePassword(String currentUserEmail, ChangePasswordDto dto) {
        Employee employee = employeeRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("Current user not found"));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), employee.getPassword())) {
            throw new RuntimeException("Wrong current password");
        }

        employee.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        employeeRepository.save(employee);
    }

    public EmployeeShortDto getByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User was not found"));

        return employeeMapper.toShortDto(employee);
    }
}