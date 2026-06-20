package com.example.adminpanelbackend.controller.impl;

import com.example.adminpanelbackend.controller.IRestCityController;
import com.example.adminpanelbackend.dto.Region.CityDto;
import com.example.adminpanelbackend.dto.Region.CityDtoUI;
import com.example.adminpanelbackend.services.ICityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class RestCityControllerImpl implements IRestCityController {

    @Autowired
    private ICityService cityService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('Admin', 'Editor', 'Viewer')")
    @Override
    public ResponseEntity<List<CityDto>> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Editor', 'Viewer')")
    @Override
    public ResponseEntity<CityDto> getCityById(@PathVariable(value = "id") Long id) {
        return cityService.getCityById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('Admin', 'Editor')")
    @Override
    public ResponseEntity<Boolean> addCity(@Valid @RequestBody CityDtoUI dto) {
        return cityService.createCity(dto);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Editor')")
    @Override
    public ResponseEntity<Boolean> updateCity(@PathVariable(value = "id") Long id, @Valid @RequestBody CityDtoUI dto) {
        return cityService.updateCity(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    @Override
    public ResponseEntity<Boolean> softDeleteCity(@PathVariable(value = "id") Long id) {
        return cityService.softDeleteCity(id);
    }

    @DeleteMapping("/delete/{id}/hard")
    @PreAuthorize("hasRole('Admin')")
    @Override
    public ResponseEntity<Boolean> hardDeleteCity(@PathVariable(value = "id") Long id) {
        return cityService.hardDeleteCity(id);
    }
}
