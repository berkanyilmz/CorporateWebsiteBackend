package com.example.adminpanelbackend.controller.impl;

import com.example.adminpanelbackend.controller.IRestUserController;
import com.example.adminpanelbackend.dto.User.UserDto;
import com.example.adminpanelbackend.dto.User.UserDtoUI;
import com.example.adminpanelbackend.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class RestUserControllerImpl implements IRestUserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/get-all")
    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/me")
    @Override
    public ResponseEntity<UserDto> me(Authentication authentication) {
        return userService.me(authentication);
    }

    @PostMapping("/insert")
    @Override
    public ResponseEntity<Boolean> insertUser(UserDtoUI userDto) {
        return userService.insertUser(userDto);
    }
}
