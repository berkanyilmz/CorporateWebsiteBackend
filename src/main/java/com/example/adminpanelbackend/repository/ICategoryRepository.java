package com.example.adminpanelbackend.repository;

import com.example.adminpanelbackend.model.Category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryName(String categoryName);

    Optional<Category> findByIdAndDeletedFalse(Long id);

    List<Category> findAllByDeletedFalse();

    boolean existsByCategoryNameAndDeletedFalse(String categoryName);

    boolean existsByCategoryNameAndDeletedFalseAndIdNot(String categoryName, Long id);

    boolean existsBySlugAndDeletedFalse(String slug);

    boolean existsBySlugAndDeletedFalseAndIdNot(String slug, Long id);
}
