package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.Auth.RegisterResponseDto;
import com.example.adminpanelbackend.dto.User.UserDtoUI;
import com.example.adminpanelbackend.jwt.JwtService;
import com.example.adminpanelbackend.model.User.Roles;
import com.example.adminpanelbackend.model.User.User;
import com.example.adminpanelbackend.repository.IRoleRepository;
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

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    @Lazy
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @Override
    public ResponseEntity<RegisterResponseDto> register(UserDtoUI userDtoUI) {
        if (userRepository.existsByUserEmail(userDtoUI.getUserEmail())) {
            return ResponseEntity.badRequest().body(new RegisterResponseDto(false, "This email is already registered."));
        }

        Roles role = roleRepository.findById(userDtoUI.getRoleId()).orElse(null);
        if (role == null) {
            return ResponseEntity.badRequest().body(new RegisterResponseDto(false, "Selected role not found."));
        }

        User user = new User();

        user.setUserFirstName(userDtoUI.getUserFirstName());
        user.setUserLastName(userDtoUI.getUserLastName());
        user.setUserEmail(userDtoUI.getUserEmail());
        user.setPasswordHash(passwordEncoder.encode(userDtoUI.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setDeleted(false);
        user.setRoleID(role);

        User savedUser = userRepository.save(user);

        if (savedUser.getId() != null)
            return ResponseEntity.ok(new RegisterResponseDto(true, "Registration successful."));
        else
            return ResponseEntity.badRequest().body(new RegisterResponseDto(false, "Registration failed."));
    }

    @Override
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie deleteCookie = jwtService.createTokenCookie("refreshToken", "", 0);
        response.addCookie(deleteCookie);
        return ResponseEntity.ok().build();
    }
}
