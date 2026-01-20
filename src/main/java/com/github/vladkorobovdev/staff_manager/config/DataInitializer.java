package com.github.vladkorobovdev.staff_manager.config;

import com.github.vladkorobovdev.staff_manager.entity.Department;
import com.github.vladkorobovdev.staff_manager.entity.Employee;
import com.github.vladkorobovdev.staff_manager.enums.Role;
import com.github.vladkorobovdev.staff_manager.repository.DepartmentRepository;
import com.github.vladkorobovdev.staff_manager.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("------------------------------");
        System.out.println("App is working...");
        System.out.println("------------------------------");

        if (employeeRepository.findByEmail("admin@staff.com").isEmpty()) {
            System.out.println("Admin was not found. Creating...");
            createAdmin();
            System.out.println("Admin was created successfully: admin@staff.com / admin");
        }
    }

    private void createAdmin() {
        Department adminDepartment = departmentRepository.findByName("Administration")
                .orElseGet(() -> {
                    System.out.println("Administration Department was not found. Creating...");
                    Department department = new Department();
                    department.setName("Administration");
                    return departmentRepository.save(department);
                });

        Employee admin = new Employee();
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setEmail("admin@staff.com");
        admin.setSalary(new BigDecimal("999999"));
        admin.setRole(Role.ADMIN);
        admin.setDepartment(adminDepartment);
        admin.setPassword(passwordEncoder.encode("admin"));

        employeeRepository.save(admin);
    }
}
