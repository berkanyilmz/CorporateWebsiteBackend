package com.example.adminpanelbackend.controller;

import com.example.adminpanelbackend.dto.Product.FeatureDto;
import com.example.adminpanelbackend.dto.Product.FeatureDtoUI;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IRestFeatureController {

    ResponseEntity<List<FeatureDto>> getAllFeatures();

    ResponseEntity<FeatureDto> getFeatureById(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> addFeature(@Valid @RequestBody FeatureDtoUI dto);

    ResponseEntity<Boolean> updateFeature(@PathVariable(value = "id") Long id, @Valid @RequestBody FeatureDtoUI dto);

    ResponseEntity<Boolean> softDeleteFeature(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> hardDeleteFeature(@PathVariable(value = "id") Long id);
}
