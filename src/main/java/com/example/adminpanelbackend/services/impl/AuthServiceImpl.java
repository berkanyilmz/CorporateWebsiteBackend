package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.Auth.RegisterResponseDto;
import com.example.adminpanelbackend.dto.User.UserRegisterDto;
import com.example.adminpanelbackend.jwt.JwtService;
import com.example.adminpanelbackend.model.User.User;
import com.example.adminpanelbackend.repository.IUserRepository;
import com.example.adminpanelbackend.services.IAuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    @Lazy
    private IUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @Override
    public RegisterResponseDto register(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByUserEmail(userRegisterDto.getUserEmail())) {
            return new RegisterResponseDto(false, "This email is already registered.");
        }
        User user = new User();
        LocalDateTime localDateTime = LocalDateTime.now();

        user.setUserFirstName(userRegisterDto.getUserFirstName());
        user.setUserLastName(userRegisterDto.getUserLastName());
        user.setUserEmail(userRegisterDto.getUserEmail());
        user.setPasswordHash(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setCreatedDate(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        user.setDeleted(false);

        User savedUser = userRepository.save(user);

        if (savedUser.getId() != null)
            return new RegisterResponseDto(true, "Registration successful.");
        else
            return new RegisterResponseDto(false, "Registration failed.");
    }

    @Override
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie deleteCookie = jwtService.createTokenCookie("refreshToken", "", 0);
        response.addCookie(deleteCookie);
        return ResponseEntity.ok().build();
    }
}
