package com.example.adminpanelbackend.controller;

import com.example.adminpanelbackend.dto.User.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IRestRoleController {

    ResponseEntity<List<RoleDto>> getAllRoles();

    ResponseEntity<Boolean> deleteRoleById(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> addRole(RoleDto roleDto);

    ResponseEntity<Long> findIdByRoleName(String roleName);

}
