package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Auth.RegisterResponseDto;
import com.example.adminpanelbackend.dto.User.UserRegisterDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthService {

    public RegisterResponseDto register(UserRegisterDto userRegisterDto);

    ResponseEntity<Void> logout(HttpServletResponse response);

}
