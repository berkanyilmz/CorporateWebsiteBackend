package com.example.adminpanelbackend.controller.impl;

import com.example.adminpanelbackend.controller.IRestCountryController;
import com.example.adminpanelbackend.dto.Region.CountryDto;
import com.example.adminpanelbackend.dto.Region.CountryDtoUI;
import com.example.adminpanelbackend.services.ICountryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class RestCountryControllerImpl implements IRestCountryController {

    @Autowired
    private ICountryService countryService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'VIEWER')")
    @Override
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'VIEWER')")
    @Override
    public ResponseEntity<CountryDto> getCountryById(@PathVariable(value = "id") Long id) {
        return countryService.getCountryById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @Override
    public ResponseEntity<Boolean> addCountry(@Valid @RequestBody CountryDtoUI dto) {
        return countryService.createCountry(dto);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @Override
    public ResponseEntity<Boolean> updateCountry(@PathVariable(value = "id") Long id, @Valid @RequestBody CountryDtoUI dto) {
        return countryService.updateCountry(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<Boolean> softDeleteCountry(@PathVariable(value = "id") Long id) {
        return countryService.softDeleteCountry(id);
    }

    @DeleteMapping("/delete/{id}/hard")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<Boolean> hardDeleteCountry(@PathVariable(value = "id") Long id) {
        return countryService.hardDeleteCountry(id);
    }
}
