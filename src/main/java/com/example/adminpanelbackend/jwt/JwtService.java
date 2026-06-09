package com.example.adminpanelbackend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.cookie.secure:false}")
    private boolean cookieSecure;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("role", "ADMIN");
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .addClaims(claimsMap)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public <T> T exportToken(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build().parseSignedClaims(token)
                .getPayload();

        return claimsTFunction.apply(claims);
    }

    public Cookie createRefreshTokenCookie(String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(cookieSecure);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);

        return refreshTokenCookie;
    }

    public boolean isTokenExpired(String token) {
        Date expiredDate = exportToken(token, Claims::getExpiration);
        return expiredDate.before(new Date());
    }

    public SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
