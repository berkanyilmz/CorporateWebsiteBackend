package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.User.UserDto;
import com.example.adminpanelbackend.model.User.User;
import com.example.adminpanelbackend.repository.IUserRepository;
import com.example.adminpanelbackend.services.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanProperty;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();

        if (users != null) {
            List<UserDto> userDtoList = users.stream().map(user -> {
                UserDto userDto = new UserDto();
                BeanUtils.copyProperties(user, userDto);
                return userDto;
            }).collect(Collectors.toList());
            return userDtoList;
        }
        return null;
    }
}
