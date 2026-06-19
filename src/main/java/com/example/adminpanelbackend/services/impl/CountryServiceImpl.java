package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.Region.CountryDto;
import com.example.adminpanelbackend.dto.Region.CountryDtoUI;
import com.example.adminpanelbackend.model.Regions.Country;
import com.example.adminpanelbackend.repository.ICountryRepository;
import com.example.adminpanelbackend.services.ICountryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements ICountryService {

    @Autowired
    private ICountryRepository countryRepository;

    @Override
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        List<Country> countries = countryRepository.findAllByDeletedFalse();
        if (countries.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<CountryDto> dtos = countries.stream().map(country -> {
            CountryDto dto = new CountryDto();
            BeanUtils.copyProperties(country, dto);
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<CountryDto> getCountryById(Long id) {
        Optional<Country> country = countryRepository.findByIdAndDeletedFalse(id);
        if (country.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        CountryDto dto = new CountryDto();
        BeanUtils.copyProperties(country.get(), dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Boolean> createCountry(CountryDtoUI dto) {
        if (countryRepository.existsByCountryNameAndDeletedFalse(dto.getCountryName())) {
            return ResponseEntity.badRequest().body(false);
        }
        Country country = new Country();
        BeanUtils.copyProperties(dto, country);
        country.setDeleted(false);
        countryRepository.save(country);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> updateCountry(Long id, CountryDtoUI dto) {
        Optional<Country> existing = countryRepository.findByIdAndDeletedFalse(id);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        if (countryRepository.existsByCountryNameAndDeletedFalseAndIdNot(dto.getCountryName(), id)) {
            return ResponseEntity.badRequest().body(false);
        }
        Country country = existing.get();
        country.setCountryName(dto.getCountryName());
        country.setCountryCode(dto.getCountryCode());
        countryRepository.save(country);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> softDeleteCountry(Long id) {
        Optional<Country> country = countryRepository.findByIdAndDeletedFalse(id);
        if (country.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        country.get().setDeleted(true);
        countryRepository.save(country.get());
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> hardDeleteCountry(Long id) {
        if (!countryRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(false);
        }
        countryRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }
}
