package com.github.vladkorobovdev.staff_manager.controller;

import com.github.vladkorobovdev.staff_manager.dto.JwtRequestDto;
import com.github.vladkorobovdev.staff_manager.dto.JwtResponseDto;
import com.github.vladkorobovdev.staff_manager.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponseDto createAuthToken(@RequestBody JwtRequestDto request) {
        return authService.login(request);
    }
}
