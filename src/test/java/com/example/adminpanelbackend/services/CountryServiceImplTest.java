package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Region.CountryDtoUI;
import com.example.adminpanelbackend.model.Regions.Country;
import com.example.adminpanelbackend.repository.ICountryRepository;
import com.example.adminpanelbackend.services.impl.CountryServiceImpl;
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
class CountryServiceImplTest {

    @Mock
    private ICountryRepository countryRepository;

    @InjectMocks
    private CountryServiceImpl countryService;

    @Test
    void createCountry_shouldReturnTrue_whenCountryNameIsUnique() {
        CountryDtoUI dto = new CountryDtoUI();
        dto.setCountryName("Turkey");
        dto.setCountryCode(90);

        when(countryRepository.existsByCountryNameAndDeletedFalse("Turkey")).thenReturn(false);

        ResponseEntity<Boolean> response = countryService.createCountry(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(countryRepository).save(any(Country.class));
    }

    @Test
    void createCountry_shouldReturnBadRequest_whenCountryNameAlreadyExists() {
        CountryDtoUI dto = new CountryDtoUI();
        dto.setCountryName("Turkey");

        when(countryRepository.existsByCountryNameAndDeletedFalse("Turkey")).thenReturn(true);

        ResponseEntity<Boolean> response = countryService.createCountry(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody());
    }
}
