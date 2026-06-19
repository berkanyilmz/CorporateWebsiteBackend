package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Region.CityDto;
import com.example.adminpanelbackend.dto.Region.CityDtoUI;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICityService {

    ResponseEntity<List<CityDto>> getAllCities();

    ResponseEntity<CityDto> getCityById(Long id);

    ResponseEntity<Boolean> createCity(CityDtoUI dto);

    ResponseEntity<Boolean> updateCity(Long id, CityDtoUI dto);

    ResponseEntity<Boolean> softDeleteCity(Long id);

    ResponseEntity<Boolean> hardDeleteCity(Long id);
}
