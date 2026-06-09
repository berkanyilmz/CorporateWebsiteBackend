package com.example.adminpanelbackend.controller;

import com.example.adminpanelbackend.dto.Auth.RegisterResponseDto;
import com.example.adminpanelbackend.dto.User.UserRegisterDto;
import com.example.adminpanelbackend.dto.Auth.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IRestAuthController {

    RegisterResponseDto register(UserRegisterDto userRegisterDto);

    AuthResponse refreshToken(HttpServletRequest request, HttpServletResponse response);

}
