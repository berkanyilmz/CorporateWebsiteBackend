package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.User.DtoUser;
import com.example.adminpanelbackend.jwt.AuthRequest;
import com.example.adminpanelbackend.jwt.AuthResponse;

public interface IAuthService {

    public AuthResponse register(AuthRequest authRequest);

}
