package com.example.adminpanelbackend.controller;

import com.example.adminpanelbackend.dto.User.UserDto;

import java.util.List;

public interface IRestUserController {

    List<UserDto> getUsers();

}
