package com.example.adminpanelbackend.controller;

import com.example.adminpanelbackend.dto.User.DtoUser;
import com.example.adminpanelbackend.dto.User.LoginRequest;
import com.example.adminpanelbackend.jwt.AuthRequest;
import com.example.adminpanelbackend.jwt.AuthResponse;

public interface IRestAuthController {

    public AuthResponse register(AuthRequest authRequest);

    public AuthResponse login(LoginRequest loginRequest);

}
