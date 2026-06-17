package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.User.RoleDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRoleService {

    ResponseEntity<List<RoleDto>> getAllRoles();

    ResponseEntity<Boolean> addRole(RoleDto roleDto);

    ResponseEntity<Long> findIdByRoleName(String roleName);

}
