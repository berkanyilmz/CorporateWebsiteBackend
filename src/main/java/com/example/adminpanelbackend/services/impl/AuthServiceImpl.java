package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.jwt.AuthRequest;
import com.example.adminpanelbackend.jwt.AuthResponse;
import com.example.adminpanelbackend.model.UserModels.User;
import com.example.adminpanelbackend.repository.IUserRepository;
import com.example.adminpanelbackend.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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

    @Override
    public AuthResponse register(AuthRequest authRequest) {
        if (userRepository.existsByUserEmail(authRequest.getUserEmail())) {
            return new AuthResponse(false, "Bu E-mail Daha Önce Alınmış");
        }
        User user = new User();
        LocalDateTime localDateTime = LocalDateTime.now();

        user.setUserFirstName(authRequest.getUserFirstName());
        user.setUserLastName(authRequest.getUserLastName());
        user.setUserEmail(authRequest.getUserEmail());
        user.setPasswordHash(passwordEncoder.encode(authRequest.getPassword()));
        user.setCreatedDate(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        user.setDeleted(false);

        User savedUser = userRepository.save(user);

        if (savedUser.getId() != null)
            return new AuthResponse(true, "Kayıt Başarılı");
        else
            return new AuthResponse(false, "Kayıt Başarısız");
    }
}
