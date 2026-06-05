package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.Auth.AuthResponse;
import com.example.adminpanelbackend.jwt.JwtService;
import com.example.adminpanelbackend.jwt.RefreshTokenRequest;
import com.example.adminpanelbackend.model.JWT.RefreshToken;
import com.example.adminpanelbackend.model.User.User;
import com.example.adminpanelbackend.repository.IRefreshTokenRepository;
import com.example.adminpanelbackend.services.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements IRefreshTokenService {

    @Autowired
    private IRefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtService jwtService;

    public boolean isTokenExpired(Date expireDate) {
        return expireDate.before(new Date());
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(new Date((System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))); // a week
        refreshToken.setUser(user);

        return  refreshToken;
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshToken) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken.getRefreshToken());

        if (optionalRefreshToken.isEmpty()) {
            throw new RuntimeException("Refresh Token Not Found: " + refreshToken.getRefreshToken());
        }

        RefreshToken refreshTokenOptional = optionalRefreshToken.get();

        if (isTokenExpired(refreshTokenOptional.getExpireDate())) {
            refreshTokenRepository.delete(refreshTokenOptional);
            throw new RuntimeException("Refresh Token Expired: " + refreshToken.getRefreshToken());
        }

        String accessToken = jwtService.generateToken(refreshTokenOptional.getUser());

        refreshTokenRepository.delete(refreshTokenOptional);
        RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(refreshTokenOptional.getUser()));
        return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
    }
}
