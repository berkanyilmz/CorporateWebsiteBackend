package com.example.adminpanelbackend.controller.impl;

import com.example.adminpanelbackend.controller.IRestUnitController;
import com.example.adminpanelbackend.dto.Product.UnitDto;
import com.example.adminpanelbackend.dto.Product.UnitDtoUI;
import com.example.adminpanelbackend.services.IUnitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/units")
public class RestUnitControllerImpl implements IRestUnitController {

    @Autowired
    private IUnitService unitService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'VIEWER')")
    @Override
    public ResponseEntity<List<UnitDto>> getAllUnits() {
        return unitService.getAllUnits();
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'VIEWER')")
    @Override
    public ResponseEntity<UnitDto> getUnitById(@PathVariable(value = "id") Long id) {
        return unitService.getUnitById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @Override
    public ResponseEntity<Boolean> addUnit(@Valid @RequestBody UnitDtoUI dto) {
        return unitService.createUnit(dto);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @Override
    public ResponseEntity<Boolean> updateUnit(@PathVariable(value = "id") Long id, @Valid @RequestBody UnitDtoUI dto) {
        return unitService.updateUnit(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<Boolean> softDeleteUnit(@PathVariable(value = "id") Long id) {
        return unitService.softDeleteUnit(id);
    }

    @DeleteMapping("/delete/{id}/hard")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<Boolean> hardDeleteUnit(@PathVariable(value = "id") Long id) {
        return unitService.hardDeleteUnit(id);
    }
}
