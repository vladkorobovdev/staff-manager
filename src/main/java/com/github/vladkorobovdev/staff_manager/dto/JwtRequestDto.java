package com.github.vladkorobovdev.staff_manager.dto;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String email;
    private String password;
}
