package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.User.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IUserService {

    List<UserDto> getUsers();

    ResponseEntity<UserDto> me(Authentication authentication);

}
