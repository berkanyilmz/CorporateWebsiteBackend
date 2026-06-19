package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.User.RoleDto;
import com.example.adminpanelbackend.model.User.Roles;
import com.example.adminpanelbackend.repository.IRoleRepository;
import com.example.adminpanelbackend.services.IRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<Roles> roles = roleRepository.findAll();

        if (roles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<RoleDto> roleDtos = roles.stream().map(role -> {
            RoleDto roleDto = new RoleDto();;
            BeanUtils.copyProperties(role, roleDto);
            return roleDto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(roleDtos);
    }

    @Override
    public ResponseEntity<Boolean> addRole(RoleDto roleDto) {
        if (roleRepository.findByRoleName(roleDto.getRoleName()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        String roleName = roleDto.getRoleName().substring(0, 1).toUpperCase() + roleDto.getRoleName().substring(1).toLowerCase();
        String roleDescription = roleDto.getRoleDescription().substring(0, 1).toUpperCase() + roleDto.getRoleDescription().substring(1).toLowerCase();
        Roles role = new Roles(roleName, roleDescription);
        roleRepository.save(role);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Long> findIdByRoleName(String roleName) {
        roleName = roleName.substring(0, 1).toUpperCase() + roleName.substring(1).toLowerCase();
        Optional<Long> roleId = roleRepository.findIdByRoleName(roleName).map(Roles::getId);
        if (roleId.isPresent()) {
            return ResponseEntity.ok(roleId.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Boolean> deleteRoleById(Long id) {
        if (!roleRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(false);
        }
        roleRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }
}
