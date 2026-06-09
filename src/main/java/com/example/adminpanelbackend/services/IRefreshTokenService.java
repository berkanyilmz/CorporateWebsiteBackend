package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.model.JWT.RefreshToken;
import com.example.adminpanelbackend.model.User.User;

public interface IRefreshTokenService {

    RefreshToken rotateRefreshToken(String refreshTokenValue);

    RefreshToken createRefreshToken(User user);

}
