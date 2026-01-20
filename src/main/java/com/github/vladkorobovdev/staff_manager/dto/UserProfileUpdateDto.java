package com.github.vladkorobovdev.staff_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserProfileUpdateDto {
    @Size(min = 3, message = "First name must be at least 3 characters long")
    private String firstName;

    @Size(min = 3, message = "Last name must be at least 3 characters long")
    private String lastName;

    @Email(message = "Invalid email format")
    private String email;
}
