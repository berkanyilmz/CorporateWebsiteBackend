package com.example.adminpanelbackend.controller;

import com.example.adminpanelbackend.dto.User.RoleDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRestRoleController {

    ResponseEntity<List<RoleDto>> getAllRoles();

    ResponseEntity<Boolean> deleteRoleById();

    ResponseEntity<Boolean> addRole(RoleDto roleDto);

    ResponseEntity<Long> findIdByRoleName(String roleName);

}
