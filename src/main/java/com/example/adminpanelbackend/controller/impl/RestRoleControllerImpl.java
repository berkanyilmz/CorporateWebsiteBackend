package com.example.adminpanelbackend.controller.impl;

import com.example.adminpanelbackend.controller.IRestRoleController;
import com.example.adminpanelbackend.dto.User.RoleDto;
import com.example.adminpanelbackend.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RestRoleControllerImpl implements IRestRoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('Admin')")
    @Override
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return roleService.getAllRoles();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    @Override
    public ResponseEntity<Boolean> deleteRoleById(@PathVariable(value = "id") Long id) {
        return roleService.deleteRoleById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('Admin')")
    @Override
    public ResponseEntity<Boolean> addRole(@RequestBody RoleDto roleDto) {
        return roleService.addRole(roleDto);
    }

    @GetMapping("/get/{roleName}")
    @PreAuthorize("hasRole('Admin')")
    @Override
    public ResponseEntity<Long> findIdByRoleName(@PathVariable(value = "roleName") String roleName) {
        return roleService.findIdByRoleName(roleName);
    }
}
