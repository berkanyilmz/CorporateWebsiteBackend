package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Auth.RegisterResponseDto;
import com.example.adminpanelbackend.dto.User.UserDtoUI;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthService {

    ResponseEntity<RegisterResponseDto> register(UserDtoUI userDtoUI);

    ResponseEntity<Void> logout(HttpServletResponse response);

}
