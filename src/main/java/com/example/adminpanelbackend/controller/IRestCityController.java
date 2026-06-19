package com.example.adminpanelbackend.controller;

import com.example.adminpanelbackend.dto.Region.CityDto;
import com.example.adminpanelbackend.dto.Region.CityDtoUI;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IRestCityController {

    ResponseEntity<List<CityDto>> getAllCities();

    ResponseEntity<CityDto> getCityById(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> addCity(@Valid @RequestBody CityDtoUI dto);

    ResponseEntity<Boolean> updateCity(@PathVariable(value = "id") Long id, @Valid @RequestBody CityDtoUI dto);

    ResponseEntity<Boolean> softDeleteCity(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> hardDeleteCity(@PathVariable(value = "id") Long id);
}
