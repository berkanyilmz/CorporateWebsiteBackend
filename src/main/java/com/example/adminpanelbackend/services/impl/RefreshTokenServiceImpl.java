package com.example.adminpanelbackend.services.impl;

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

    public boolean isTokenExpired(Date expireDate) {
        return expireDate.before(new Date());
    }

    @Override
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(new Date((System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7))); // a week
        refreshToken.setUser(user);
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken rotateRefreshToken(String refreshTokenValue) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenValue);

        if (optionalRefreshToken.isEmpty()) {
            throw new RuntimeException("Refresh Token Not Found: " + refreshTokenValue);
        }

        RefreshToken oldToken = optionalRefreshToken.get();

        if (isTokenExpired(oldToken.getExpireDate())) {
            refreshTokenRepository.delete(oldToken);
            throw new RuntimeException("Refresh Token Expired: " + refreshTokenValue);
        }

        refreshTokenRepository.delete(oldToken);
        return createRefreshToken(oldToken.getUser());
    }
}
