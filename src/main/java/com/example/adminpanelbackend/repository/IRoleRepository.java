package com.example.adminpanelbackend.repository;

import com.example.adminpanelbackend.model.User.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Roles, Long> {


    Optional<Roles> findByRoleName(String roleName);

    Optional<Roles> findIdByRoleName(String roleName);
}
