package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Region.CityDtoUI;
import com.example.adminpanelbackend.model.Regions.City;
import com.example.adminpanelbackend.repository.ICityRepository;
import com.example.adminpanelbackend.services.impl.CityServiceImpl;
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
class CityServiceImplTest {

    @Mock
    private ICityRepository cityRepository;

    @InjectMocks
    private CityServiceImpl cityService;

    @Test
    void createCity_shouldReturnTrue_whenCityNameIsUnique() {
        CityDtoUI dto = new CityDtoUI();
        dto.setCityName("Istanbul");

        when(cityRepository.existsByCityNameAndDeletedFalse("Istanbul")).thenReturn(false);

        ResponseEntity<Boolean> response = cityService.createCity(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(cityRepository).save(any(City.class));
    }

    @Test
    void createCity_shouldReturnBadRequest_whenCityNameAlreadyExists() {
        CityDtoUI dto = new CityDtoUI();
        dto.setCityName("Istanbul");

        when(cityRepository.existsByCityNameAndDeletedFalse("Istanbul")).thenReturn(true);

        ResponseEntity<Boolean> response = cityService.createCity(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody());
    }
}
