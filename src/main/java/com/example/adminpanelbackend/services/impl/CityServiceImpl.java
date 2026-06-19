package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.Region.CityDto;
import com.example.adminpanelbackend.dto.Region.CityDtoUI;
import com.example.adminpanelbackend.model.Regions.City;
import com.example.adminpanelbackend.repository.ICityRepository;
import com.example.adminpanelbackend.services.ICityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements ICityService {

    @Autowired
    private ICityRepository cityRepository;

    @Override
    public ResponseEntity<List<CityDto>> getAllCities() {
        List<City> cities = cityRepository.findAllByDeletedFalse();
        if (cities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<CityDto> dtos = cities.stream().map(city -> {
            CityDto dto = new CityDto();
            BeanUtils.copyProperties(city, dto);
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<CityDto> getCityById(Long id) {
        Optional<City> city = cityRepository.findByIdAndDeletedFalse(id);
        if (city.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        CityDto dto = new CityDto();
        BeanUtils.copyProperties(city.get(), dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Boolean> createCity(CityDtoUI dto) {
        if (cityRepository.existsByCityNameAndDeletedFalse(dto.getCityName())) {
            return ResponseEntity.badRequest().body(false);
        }
        City city = new City();
        BeanUtils.copyProperties(dto, city);
        city.setDeleted(false);
        cityRepository.save(city);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> updateCity(Long id, CityDtoUI dto) {
        Optional<City> existing = cityRepository.findByIdAndDeletedFalse(id);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        if (cityRepository.existsByCityNameAndDeletedFalseAndIdNot(dto.getCityName(), id)) {
            return ResponseEntity.badRequest().body(false);
        }
        City city = existing.get();
        city.setCityName(dto.getCityName());
        cityRepository.save(city);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> softDeleteCity(Long id) {
        Optional<City> city = cityRepository.findByIdAndDeletedFalse(id);
        if (city.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        city.get().setDeleted(true);
        cityRepository.save(city.get());
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> hardDeleteCity(Long id) {
        if (!cityRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(false);
        }
        cityRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }
}
