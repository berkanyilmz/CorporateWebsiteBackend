package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Product.FeatureDtoUI;
import com.example.adminpanelbackend.model.Product.Feature;
import com.example.adminpanelbackend.repository.IFeatureRepository;
import com.example.adminpanelbackend.services.impl.FeatureServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeatureServiceImplTest {

    @Mock
    private IFeatureRepository featureRepository;

    @InjectMocks
    private FeatureServiceImpl featureService;

    @Test
    void createFeature_shouldReturnTrue_whenFeatureNameIsUnique() {
        FeatureDtoUI dto = new FeatureDtoUI();
        dto.setFeatureName("Color");

        when(featureRepository.existsByFeatureNameAndDeletedFalse("Color")).thenReturn(false);

        ResponseEntity<Boolean> response = featureService.createFeature(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(featureRepository).save(any(Feature.class));
    }

    @Test
    void createFeature_shouldReturnBadRequest_whenFeatureNameAlreadyExists() {
        FeatureDtoUI dto = new FeatureDtoUI();
        dto.setFeatureName("Color");

        when(featureRepository.existsByFeatureNameAndDeletedFalse("Color")).thenReturn(true);

        ResponseEntity<Boolean> response = featureService.createFeature(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody());
        verify(featureRepository, never()).save(any(Feature.class));
    }
}
