package com.example.adminpanelbackend.jwt;

import com.example.adminpanelbackend.dto.Auth.AuthRequest;
import com.example.adminpanelbackend.dto.Auth.AuthResponse;
import com.example.adminpanelbackend.dto.User.LoginRequestDto;
import com.example.adminpanelbackend.model.JWT.RefreshToken;
import com.example.adminpanelbackend.model.User.User;
import com.example.adminpanelbackend.services.IRefreshTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CustomJsonLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IRefreshTokenService refreshTokenService;

    public CustomJsonLoginFilter(AuthenticationManager authenticationManager, JwtService jwtService, IRefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        setFilterProcessesUrl("/auth/login"); // Set the login endpoint
    }

    // Read JSON and Validate
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthRequest loginRequestDto = new ObjectMapper().readValue(request.getInputStream(), AuthRequest.class);
            return  authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUserEmail(), loginRequestDto.getPassword())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // getPrincipal() nesnesini kendi User entity'mize cast ediyoruz
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        String accessToken = jwtService.generateToken(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken((User) authResult.getPrincipal());
        String refreshTokenObj = refreshToken.getRefreshToken();

        response.addCookie(jwtService.createRefreshTokenCookie(refreshTokenObj));

        // JSON Yanıtı
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        AuthResponse authResponse = new AuthResponse(accessToken);
        String jsonStr = new ObjectMapper().writeValueAsString(authResponse);

        response.getWriter().write(jsonStr);
    }
}
