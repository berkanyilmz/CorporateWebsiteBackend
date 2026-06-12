package com.example.adminpanelbackend.controller.impl;

import com.example.adminpanelbackend.controller.IRestAuthController;
import com.example.adminpanelbackend.dto.Auth.RegisterResponseDto;
import com.example.adminpanelbackend.dto.User.UserDto;
import com.example.adminpanelbackend.dto.User.UserRegisterDto;
import com.example.adminpanelbackend.dto.Auth.AuthResponse;
import com.example.adminpanelbackend.jwt.JwtService;
import com.example.adminpanelbackend.model.JWT.RefreshToken;
import com.example.adminpanelbackend.services.IAuthService;
import com.example.adminpanelbackend.services.IRefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RestAuthControllerImpl implements IRestAuthController {

    @Autowired
    private IAuthService authService;

    @Autowired
    private IRefreshTokenService refreshTokenService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    @Override
    public RegisterResponseDto register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        return authService.register(userRegisterDto);
    }

    @PostMapping("/refresh")
    @Override
    public AuthResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new RuntimeException("Refresh token is missing");
        }

        RefreshToken newRefreshToken = refreshTokenService.rotateRefreshToken(refreshToken);
        String accessToken = jwtService.generateToken(newRefreshToken.getUser());
        response.addCookie(jwtService.createRefreshTokenCookie(newRefreshToken.getRefreshToken()));

        return new AuthResponse(accessToken);
    }

}
