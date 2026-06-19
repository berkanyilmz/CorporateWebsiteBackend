package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.Product.FeatureDto;
import com.example.adminpanelbackend.dto.Product.FeatureDtoUI;
import com.example.adminpanelbackend.model.Product.Feature;
import com.example.adminpanelbackend.repository.IFeatureRepository;
import com.example.adminpanelbackend.services.IFeatureService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeatureServiceImpl implements IFeatureService {

    @Autowired
    private IFeatureRepository featureRepository;

    @Override
    public ResponseEntity<List<FeatureDto>> getAllFeatures() {
        List<Feature> features = featureRepository.findAllByDeletedFalse();
        if (features.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<FeatureDto> dtos = features.stream().map(feature -> {
            FeatureDto dto = new FeatureDto();
            BeanUtils.copyProperties(feature, dto);
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<FeatureDto> getFeatureById(Long id) {
        Optional<Feature> feature = featureRepository.findByIdAndDeletedFalse(id);
        if (feature.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        FeatureDto dto = new FeatureDto();
        BeanUtils.copyProperties(feature.get(), dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Boolean> createFeature(FeatureDtoUI dto) {
        if (featureRepository.existsByFeatureNameAndDeletedFalse(dto.getFeatureName())) {
            return ResponseEntity.badRequest().body(false);
        }
        Feature feature = new Feature();
        BeanUtils.copyProperties(dto, feature);
        feature.setDeleted(false);
        featureRepository.save(feature);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> updateFeature(Long id, FeatureDtoUI dto) {
        Optional<Feature> existing = featureRepository.findByIdAndDeletedFalse(id);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        if (featureRepository.existsByFeatureNameAndDeletedFalseAndIdNot(dto.getFeatureName(), id)) {
            return ResponseEntity.badRequest().body(false);
        }
        Feature feature = existing.get();
        feature.setFeatureName(dto.getFeatureName());
        featureRepository.save(feature);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> softDeleteFeature(Long id) {
        Optional<Feature> feature = featureRepository.findByIdAndDeletedFalse(id);
        if (feature.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        feature.get().setDeleted(true);
        featureRepository.save(feature.get());
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> hardDeleteFeature(Long id) {
        if (!featureRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(false);
        }
        featureRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }
}
