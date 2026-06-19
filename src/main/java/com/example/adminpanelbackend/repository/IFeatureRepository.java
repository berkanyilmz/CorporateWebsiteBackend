package com.example.adminpanelbackend.repository;

import com.example.adminpanelbackend.model.Product.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFeatureRepository extends JpaRepository<Feature, Long> {

    Optional<Feature> findByFeatureName(String featureName);

    Optional<Feature> findByIdAndDeletedFalse(Long id);

    List<Feature> findAllByDeletedFalse();

    boolean existsByFeatureNameAndDeletedFalse(String featureName);

    boolean existsByFeatureNameAndDeletedFalseAndIdNot(String featureName, Long id);
}
