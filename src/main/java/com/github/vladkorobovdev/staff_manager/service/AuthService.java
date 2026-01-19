package com.github.vladkorobovdev.staff_manager.service;

import com.github.vladkorobovdev.staff_manager.dto.JwtRequestDto;
import com.github.vladkorobovdev.staff_manager.dto.JwtResponseDto;
import com.github.vladkorobovdev.staff_manager.dto.RefreshTokenRequestDto;
import com.github.vladkorobovdev.staff_manager.entity.Employee;
import com.github.vladkorobovdev.staff_manager.entity.RefreshToken;
import com.github.vladkorobovdev.staff_manager.repository.EmployeeRepository;
import com.github.vladkorobovdev.staff_manager.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final EmployeeRepository employeeRepository;
    private final RefreshTokenService refreshTokenService;

    public JwtResponseDto login(JwtRequestDto loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        Employee employee = employeeRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + loginRequest.getEmail()));

        String accessToken = jwtUtils.generateToken(employee.getEmail(), employee.getRole().name());

        refreshTokenService.deleteByUserId(employee.getId());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(employee.getEmail());

        return new JwtResponseDto(accessToken, refreshToken.getToken());
    }

    public JwtResponseDto getAccessTokenByRefreshToken(RefreshTokenRequestDto requestDto) {
        RefreshToken token = refreshTokenService.findByToken(requestDto.getRefreshToken());

        refreshTokenService.verifyExpiration(token);

        Employee employee = token.getEmployee();
        String accessToken = jwtUtils.generateToken(employee.getEmail(), employee.getRole().name());

        return new JwtResponseDto(accessToken, requestDto.getRefreshToken());
    }
}
