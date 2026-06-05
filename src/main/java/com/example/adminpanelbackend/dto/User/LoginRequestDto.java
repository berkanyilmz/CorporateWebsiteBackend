package com.example.adminpanelbackend.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequestDto {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
