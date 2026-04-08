package com.takehome.stayease.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(
      regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$",
      message = "Password must be at least 8 chars, contain uppercase, number, special char"
    )
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String role;
}

