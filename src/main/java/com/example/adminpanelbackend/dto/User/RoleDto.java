package com.example.adminpanelbackend.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDto {

    private Long id;

    private String roleName;

    private String roleDescription;

}
