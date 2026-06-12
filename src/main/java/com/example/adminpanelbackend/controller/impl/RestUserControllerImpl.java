package com.example.adminpanelbackend.controller.impl;

import com.example.adminpanelbackend.controller.IRestUserController;
import com.example.adminpanelbackend.dto.User.UserDto;
import com.example.adminpanelbackend.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }
}
