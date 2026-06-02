package com.example.adminpanelbackend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    private static final String SECRET_KEY = "KBpPOcSJ88uryIFZ9c0NDc3AaWkyz49Yhgzl5uNU8no=";

    public String generateToken(UserDetails userDetails) {
        Map<String, String> claimsMap = new HashMap<>();
        claimsMap.put("role", "ADMIN");
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setClaims(claimsMap)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 7200))
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

    public String getUserNameByToken(String token) {
        return exportToken(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expiredDate = exportToken(token, Claims::getExpiration);
        return new Date().before((expiredDate));
    }

    public SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

}
