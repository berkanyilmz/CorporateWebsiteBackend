package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Auth.RegisterResponseDto;
import com.example.adminpanelbackend.dto.User.UserRegisterDto;

public interface IAuthService {

    public RegisterResponseDto register(UserRegisterDto userRegisterDto);

}
