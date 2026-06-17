package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.User.UserDto;
import com.example.adminpanelbackend.dto.User.UserDtoUI;
import com.example.adminpanelbackend.model.User.Roles;
import com.example.adminpanelbackend.model.User.User;
import com.example.adminpanelbackend.repository.IRoleRepository;
import com.example.adminpanelbackend.repository.IUserRepository;
import com.example.adminpanelbackend.services.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userRepository.findAll();

        if (users != null) {
            List<UserDto> userDtoList = users.stream().map(user -> {
                UserDto userDto = new UserDto();
                BeanUtils.copyProperties(user, userDto);
                return userDto;
            }).collect(Collectors.toList());
            return ResponseEntity.ok(userDtoList);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<UserDto> me(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userName = jwt.getSubject();
        Optional<User> user = userRepository.findByUserEmail(userName);

        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user.get(), userDto);
        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<Boolean> insertUser(UserDtoUI userDtoUI) {
        if (userRepository.existsByUserEmail(userDtoUI.getUserEmail())) {
            return ResponseEntity.badRequest().body(false);
        }

        Optional<Roles> role = roleRepository.findById(userDtoUI.getRoleId());
        if (role.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        User user = new User();
        BeanUtils.copyProperties(userDtoUI, user);
        user.setPasswordHash(passwordEncoder.encode(userDtoUI.getPassword()));
        user.setRoleID(role.get());
        user.setCreatedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        user.setDeleted(false);

        User savedUser = userRepository.save(user);
        if (savedUser.getId() != null)
            return ResponseEntity.ok(true);
        else
            return ResponseEntity.badRequest().body(false);
    }
}
