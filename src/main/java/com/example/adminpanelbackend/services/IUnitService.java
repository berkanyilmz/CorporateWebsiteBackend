package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Product.UnitDto;
import com.example.adminpanelbackend.dto.Product.UnitDtoUI;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUnitService {

    ResponseEntity<List<UnitDto>> getAllUnits();

    ResponseEntity<UnitDto> getUnitById(Long id);

    ResponseEntity<Boolean> createUnit(UnitDtoUI dto);

    ResponseEntity<Boolean> updateUnit(Long id, UnitDtoUI dto);

    ResponseEntity<Boolean> softDeleteUnit(Long id);

    ResponseEntity<Boolean> hardDeleteUnit(Long id);
}
