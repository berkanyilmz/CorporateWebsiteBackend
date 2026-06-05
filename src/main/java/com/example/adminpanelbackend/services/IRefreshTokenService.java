package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Auth.AuthResponse;
import com.example.adminpanelbackend.jwt.RefreshTokenRequest;
import com.example.adminpanelbackend.model.JWT.RefreshToken;
import com.example.adminpanelbackend.model.User.User;

public interface IRefreshTokenService {

    public AuthResponse refreshToken(RefreshTokenRequest refreshToken);

    public RefreshToken createRefreshToken(User user);

}
