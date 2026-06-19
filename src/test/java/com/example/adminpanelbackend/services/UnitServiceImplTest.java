package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Product.UnitDtoUI;
import com.example.adminpanelbackend.model.Product.Unit;
import com.example.adminpanelbackend.repository.IUnitRepository;
import com.example.adminpanelbackend.services.impl.UnitServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitServiceImplTest {

    @Mock
    private IUnitRepository unitRepository;

    @InjectMocks
    private UnitServiceImpl unitService;

    @Test
    void createUnit_shouldReturnTrue_whenUnitNameIsUnique() {
        UnitDtoUI dto = new UnitDtoUI();
        dto.setUnitName("Kilogram");

        when(unitRepository.existsByUnitNameAndDeletedFalse("Kilogram")).thenReturn(false);

        ResponseEntity<Boolean> response = unitService.createUnit(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(unitRepository).save(any(Unit.class));
    }

    @Test
    void createUnit_shouldReturnBadRequest_whenUnitNameAlreadyExists() {
        UnitDtoUI dto = new UnitDtoUI();
        dto.setUnitName("Kilogram");

        when(unitRepository.existsByUnitNameAndDeletedFalse("Kilogram")).thenReturn(true);

        ResponseEntity<Boolean> response = unitService.createUnit(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody());
        verify(unitRepository, never()).save(any(Unit.class));
    }
}
