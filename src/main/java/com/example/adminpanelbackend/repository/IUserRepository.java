package com.example.adminpanelbackend.repository;

import com.example.adminpanelbackend.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserEmail(String userEmail);

    Boolean existsByUserEmail(String email);

}
