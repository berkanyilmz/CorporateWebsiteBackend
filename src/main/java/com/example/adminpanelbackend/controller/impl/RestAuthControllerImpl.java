package com.example.adminpanelbackend.controller.impl;

import com.example.adminpanelbackend.controller.IRestAuthController;
import com.example.adminpanelbackend.dto.User.DtoUser;
import com.example.adminpanelbackend.dto.User.LoginRequest;
import com.example.adminpanelbackend.jwt.AuthRequest;
import com.example.adminpanelbackend.jwt.AuthResponse;
import com.example.adminpanelbackend.services.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthControllerImpl implements IRestAuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/register")
    @Override
    public AuthResponse register(@Valid @RequestBody AuthRequest authRequest) {
        return authService.register(authRequest);
    }

    @PostMapping("/login")
    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        return null;
    }
}
