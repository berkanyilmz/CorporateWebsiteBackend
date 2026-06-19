package com.example.adminpanelbackend.repository;

import com.example.adminpanelbackend.model.Product.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Products, Long> {

    Optional<Products> findBySlug(String slug);

    Optional<Products> findByIdAndDeletedFalse(Long id);

    List<Products> findAllByDeletedFalse();

    boolean existsBySlugAndDeletedFalse(String slug);

    boolean existsBySlugAndDeletedFalseAndIdNot(String slug, Long id);
}
