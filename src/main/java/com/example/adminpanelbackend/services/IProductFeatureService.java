package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Product.ProductFeatureDto;
import com.example.adminpanelbackend.dto.Product.ProductFeatureDtoUI;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProductFeatureService {

    ResponseEntity<List<ProductFeatureDto>> getFeaturesByProductId(Long productId);

    ResponseEntity<ProductFeatureDto> getFeatureById(Long id);

    ResponseEntity<Boolean> addFeatureToProduct(Long productId, ProductFeatureDtoUI dto);

    ResponseEntity<Boolean> updateFeature(Long productId, Long featureId, ProductFeatureDtoUI dto);

    ResponseEntity<Boolean> deleteFeature(Long productId, Long featureId);
}
