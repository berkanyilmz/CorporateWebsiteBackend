package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.Product.UnitDto;
import com.example.adminpanelbackend.dto.Product.UnitDtoUI;
import com.example.adminpanelbackend.model.Product.Unit;
import com.example.adminpanelbackend.repository.IUnitRepository;
import com.example.adminpanelbackend.services.IUnitService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnitServiceImpl implements IUnitService {

    @Autowired
    private IUnitRepository unitRepository;

    @Override
    public ResponseEntity<List<UnitDto>> getAllUnits() {
        List<Unit> units = unitRepository.findAllByDeletedFalse();
        if (units.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<UnitDto> dtos = units.stream().map(unit -> {
            UnitDto dto = new UnitDto();
            BeanUtils.copyProperties(unit, dto);
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<UnitDto> getUnitById(Long id) {
        Optional<Unit> unit = unitRepository.findByIdAndDeletedFalse(id);
        if (unit.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UnitDto dto = new UnitDto();
        BeanUtils.copyProperties(unit.get(), dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Boolean> createUnit(UnitDtoUI dto) {
        if (unitRepository.existsByUnitNameAndDeletedFalse(dto.getUnitName())) {
            return ResponseEntity.badRequest().body(false);
        }
        Unit unit = new Unit();
        BeanUtils.copyProperties(dto, unit);
        unit.setDeleted(false);
        unitRepository.save(unit);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> updateUnit(Long id, UnitDtoUI dto) {
        Optional<Unit> existing = unitRepository.findByIdAndDeletedFalse(id);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        if (unitRepository.existsByUnitNameAndDeletedFalseAndIdNot(dto.getUnitName(), id)) {
            return ResponseEntity.badRequest().body(false);
        }
        Unit unit = existing.get();
        unit.setUnitName(dto.getUnitName());
        unitRepository.save(unit);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> softDeleteUnit(Long id) {
        Optional<Unit> unit = unitRepository.findByIdAndDeletedFalse(id);
        if (unit.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        unit.get().setDeleted(true);
        unitRepository.save(unit.get());
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> hardDeleteUnit(Long id) {
        if (!unitRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(false);
        }
        unitRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }
}
