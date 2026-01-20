package com.github.vladkorobovdev.staff_manager.controller;

import com.github.vladkorobovdev.staff_manager.dto.ChangePasswordDto;
import com.github.vladkorobovdev.staff_manager.dto.EmployeeShortDto;
import com.github.vladkorobovdev.staff_manager.dto.UserProfileUpdateDto;
import com.github.vladkorobovdev.staff_manager.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final EmployeeService employeeService;

    @GetMapping
    public EmployeeShortDto getMyProfile(Principal principal) {
        return employeeService.getByEmail(principal.getName());
    }

    @PutMapping("/details")
    public EmployeeShortDto updateDetails(
            @RequestBody @Valid UserProfileUpdateDto dto,
            Principal principal
    ) {
        return employeeService.updateProfile(principal.getName(), dto);
    }

    @PutMapping("/password")
    public void changePassword(
            @RequestBody @Valid ChangePasswordDto dto,
            Principal principal
    ) {
        employeeService.changePassword(principal.getName(), dto);
    }
}
