package com.example.adminpanelbackend.repository;

import com.example.adminpanelbackend.model.Product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findAllByProductId(Long productId);

    Optional<ProductImage> findByIdAndProductId(Long id, Long productId);

    Optional<ProductImage> findByProductIdAndIsMainTrue(Long productId);
}
