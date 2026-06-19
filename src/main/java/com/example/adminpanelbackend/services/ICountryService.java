package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Region.CountryDto;
import com.example.adminpanelbackend.dto.Region.CountryDtoUI;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICountryService {

    ResponseEntity<List<CountryDto>> getAllCountries();

    ResponseEntity<CountryDto> getCountryById(Long id);

    ResponseEntity<Boolean> createCountry(CountryDtoUI dto);

    ResponseEntity<Boolean> updateCountry(Long id, CountryDtoUI dto);

    ResponseEntity<Boolean> softDeleteCountry(Long id);

    ResponseEntity<Boolean> hardDeleteCountry(Long id);
}
