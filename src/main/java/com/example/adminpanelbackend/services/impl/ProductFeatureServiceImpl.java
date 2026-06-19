package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.Product.ProductFeatureDto;
import com.example.adminpanelbackend.dto.Product.ProductFeatureDtoUI;
import com.example.adminpanelbackend.model.Product.Feature;
import com.example.adminpanelbackend.model.Product.ProductFeature;
import com.example.adminpanelbackend.model.Product.Products;
import com.example.adminpanelbackend.model.Product.Unit;
import com.example.adminpanelbackend.repository.IFeatureRepository;
import com.example.adminpanelbackend.repository.IProductFeatureRepository;
import com.example.adminpanelbackend.repository.IProductRepository;
import com.example.adminpanelbackend.repository.IUnitRepository;
import com.example.adminpanelbackend.services.IProductFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductFeatureServiceImpl implements IProductFeatureService {

    @Autowired
    private IProductFeatureRepository productFeatureRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IFeatureRepository featureRepository;

    @Autowired
    private IUnitRepository unitRepository;

    @Override
    public ResponseEntity<List<ProductFeatureDto>> getFeaturesByProductId(Long productId) {
        if (!productRepository.existsById(productId)) {
            return ResponseEntity.notFound().build();
        }
        List<ProductFeature> features = productFeatureRepository.findAllByProductId(productId);
        List<ProductFeatureDto> dtos = features.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<ProductFeatureDto> getFeatureById(Long id) {
        Optional<ProductFeature> feature = productFeatureRepository.findById(id);
        if (feature.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapToDto(feature.get()));
    }

    @Override
    public ResponseEntity<Boolean> addFeatureToProduct(Long productId, ProductFeatureDtoUI dto) {
        Optional<Products> product = productRepository.findByIdAndDeletedFalse(productId);
        if (product.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        Optional<Feature> feature = featureRepository.findByIdAndDeletedFalse(dto.getFeatureId());
        if (feature.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        Unit unit = null;
        if (dto.getUnitId() != null) {
            Optional<Unit> unitOpt = unitRepository.findByIdAndDeletedFalse(dto.getUnitId());
            if (unitOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(false);
            }
            unit = unitOpt.get();
        }

        ProductFeature productFeature = new ProductFeature();
        productFeature.setProduct(product.get());
        productFeature.setFeature(feature.get());
        productFeature.setUnit(unit);
        productFeature.setFeatureValue(dto.getFeatureValue());
        productFeatureRepository.save(productFeature);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> updateFeature(Long productId, Long featureId, ProductFeatureDtoUI dto) {
        Optional<ProductFeature> existing = productFeatureRepository.findByIdAndProductId(featureId, productId);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        Optional<Feature> feature = featureRepository.findByIdAndDeletedFalse(dto.getFeatureId());
        if (feature.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        Unit unit = null;
        if (dto.getUnitId() != null) {
            Optional<Unit> unitOpt = unitRepository.findByIdAndDeletedFalse(dto.getUnitId());
            if (unitOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(false);
            }
            unit = unitOpt.get();
        }

        ProductFeature productFeature = existing.get();
        productFeature.setFeature(feature.get());
        productFeature.setUnit(unit);
        productFeature.setFeatureValue(dto.getFeatureValue());
        productFeatureRepository.save(productFeature);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> deleteFeature(Long productId, Long featureId) {
        Optional<ProductFeature> existing = productFeatureRepository.findByIdAndProductId(featureId, productId);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        productFeatureRepository.delete(existing.get());
        return ResponseEntity.ok(true);
    }

    private ProductFeatureDto mapToDto(ProductFeature feature) {
        ProductFeatureDto dto = new ProductFeatureDto();
        dto.setId(feature.getId());
        dto.setProductId(feature.getProduct().getId());
        dto.setFeatureId(feature.getFeature().getId());
        dto.setFeatureName(feature.getFeature().getFeatureName());
        if (feature.getUnit() != null) {
            dto.setUnitId(feature.getUnit().getId());
            dto.setUnitName(feature.getUnit().getUnitName());
        }
        dto.setFeatureValue(feature.getFeatureValue());
        return dto;
    }
}
