package com.example.adminpanelbackend.controller;

import com.example.adminpanelbackend.dto.Product.UnitDto;
import com.example.adminpanelbackend.dto.Product.UnitDtoUI;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IRestUnitController {

    ResponseEntity<List<UnitDto>> getAllUnits();

    ResponseEntity<UnitDto> getUnitById(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> addUnit(@Valid @RequestBody UnitDtoUI dto);

    ResponseEntity<Boolean> updateUnit(@PathVariable(value = "id") Long id, @Valid @RequestBody UnitDtoUI dto);

    ResponseEntity<Boolean> softDeleteUnit(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> hardDeleteUnit(@PathVariable(value = "id") Long id);
}
