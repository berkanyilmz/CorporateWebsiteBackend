package com.example.adminpanelbackend.repository;

import com.example.adminpanelbackend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndDeletedFalse(Long id);

    List<Post> findAllByDeletedFalse();

    boolean existsByPostUrlAndDeletedFalse(String postUrl);

    boolean existsByPostUrlAndDeletedFalseAndIdNot(String postUrl, Long id);
}
