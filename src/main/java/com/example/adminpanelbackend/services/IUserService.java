package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.User.UserDto;

import java.util.List;

public interface IUserService {

    List<UserDto> getUsers();

}
