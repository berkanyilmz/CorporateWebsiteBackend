package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.User.UserDto;
import com.example.adminpanelbackend.dto.User.UserRegisterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IUserService {

    ResponseEntity<List<UserDto>> getUsers();

    ResponseEntity<UserDto> me(Authentication authentication);

    ResponseEntity<Boolean> addUser(UserRegisterDto userRegisterDto);

}
