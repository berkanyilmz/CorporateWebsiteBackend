package com.example.adminpanelbackend.controller;

import com.example.adminpanelbackend.dto.Region.CountryDto;
import com.example.adminpanelbackend.dto.Region.CountryDtoUI;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IRestCountryController {

    ResponseEntity<List<CountryDto>> getAllCountries();

    ResponseEntity<CountryDto> getCountryById(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> addCountry(@Valid @RequestBody CountryDtoUI dto);

    ResponseEntity<Boolean> updateCountry(@PathVariable(value = "id") Long id, @Valid @RequestBody CountryDtoUI dto);

    ResponseEntity<Boolean> softDeleteCountry(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> hardDeleteCountry(@PathVariable(value = "id") Long id);
}
