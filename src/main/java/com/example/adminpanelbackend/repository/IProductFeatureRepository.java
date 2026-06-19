package com.example.adminpanelbackend.repository;

import com.example.adminpanelbackend.model.Product.ProductFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductFeatureRepository extends JpaRepository<ProductFeature, Long> {

    List<ProductFeature> findAllByProductId(Long productId);

    Optional<ProductFeature> findByIdAndProductId(Long id, Long productId);

    boolean existsByProductIdAndFeatureId(Long productId, Long featureId);
}
