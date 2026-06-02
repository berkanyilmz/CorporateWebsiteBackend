package com.example.adminpanelbackend.jwt;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @NotEmpty
    private String userFirstName;

    @NotEmpty
    private String userLastName;

    @NotEmpty
    private String userEmail;

    @NotEmpty
    private String password;

}
