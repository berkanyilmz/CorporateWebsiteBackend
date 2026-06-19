package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Product.FeatureDto;
import com.example.adminpanelbackend.dto.Product.FeatureDtoUI;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IFeatureService {

    ResponseEntity<List<FeatureDto>> getAllFeatures();

    ResponseEntity<FeatureDto> getFeatureById(Long id);

    ResponseEntity<Boolean> createFeature(FeatureDtoUI dto);

    ResponseEntity<Boolean> updateFeature(Long id, FeatureDtoUI dto);

    ResponseEntity<Boolean> softDeleteFeature(Long id);

    ResponseEntity<Boolean> hardDeleteFeature(Long id);
}
