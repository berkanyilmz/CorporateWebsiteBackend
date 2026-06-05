package com.example.adminpanelbackend.controller;

import com.example.adminpanelbackend.dto.Auth.RegisterResponseDto;
import com.example.adminpanelbackend.dto.User.UserRegisterDto;
import com.example.adminpanelbackend.dto.Auth.AuthRequest;
import com.example.adminpanelbackend.dto.Auth.AuthResponse;
import com.example.adminpanelbackend.jwt.RefreshTokenRequest;

public interface IRestAuthController {

    public RegisterResponseDto register(UserRegisterDto userRegisterDto);

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);


}
