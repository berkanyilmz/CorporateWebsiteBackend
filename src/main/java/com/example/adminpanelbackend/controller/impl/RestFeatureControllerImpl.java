package com.example.adminpanelbackend.controller.impl;

import com.example.adminpanelbackend.controller.IRestFeatureController;
import com.example.adminpanelbackend.dto.Product.FeatureDto;
import com.example.adminpanelbackend.dto.Product.FeatureDtoUI;
import com.example.adminpanelbackend.services.IFeatureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/features")
public class RestFeatureControllerImpl implements IRestFeatureController {

    @Autowired
    private IFeatureService featureService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('Admin', 'Editor', 'Viewer')")
    @Override
    public ResponseEntity<List<FeatureDto>> getAllFeatures() {
        return featureService.getAllFeatures();
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Editor', 'Viewer')")
    @Override
    public ResponseEntity<FeatureDto> getFeatureById(@PathVariable(value = "id") Long id) {
        return featureService.getFeatureById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('Admin', 'Editor')")
    @Override
    public ResponseEntity<Boolean> addFeature(@Valid @RequestBody FeatureDtoUI dto) {
        return featureService.createFeature(dto);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Editor')")
    @Override
    public ResponseEntity<Boolean> updateFeature(@PathVariable(value = "id") Long id, @Valid @RequestBody FeatureDtoUI dto) {
        return featureService.updateFeature(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    @Override
    public ResponseEntity<Boolean> softDeleteFeature(@PathVariable(value = "id") Long id) {
        return featureService.softDeleteFeature(id);
    }

    @DeleteMapping("/delete/{id}/hard")
    @PreAuthorize("hasRole('Admin')")
    @Override
    public ResponseEntity<Boolean> hardDeleteFeature(@PathVariable(value = "id") Long id) {
        return featureService.hardDeleteFeature(id);
    }
}
