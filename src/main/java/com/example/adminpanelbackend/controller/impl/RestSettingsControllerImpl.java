package com.example.adminpanelbackend.controller.impl;

import com.example.adminpanelbackend.controller.IRestSettingsController;
import com.example.adminpanelbackend.dto.Page.SettingsDto;
import com.example.adminpanelbackend.dto.Page.SettingsDtoUI;
import com.example.adminpanelbackend.services.ISettingsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/settings")
public class RestSettingsControllerImpl implements IRestSettingsController {

    @Autowired
    private ISettingsService settingsService;

    @GetMapping("/all")
    @Override
    public ResponseEntity<List<SettingsDto>> getAllSettings() {
        return settingsService.getAllSettings();
    }

    @GetMapping("/get/{id}")
    @Override
    public ResponseEntity<SettingsDto> getSettingsById(@PathVariable(value = "id") Long id) {
        return settingsService.getSettingsById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('Admin', 'Editor')")
    @Override
    public ResponseEntity<Boolean> addSettings(@Valid @RequestBody SettingsDtoUI dto) {
        return settingsService.createSettings(dto);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Editor')")
    @Override
    public ResponseEntity<Boolean> updateSettings(@PathVariable(value = "id") Long id, @Valid @RequestBody SettingsDtoUI dto) {
        return settingsService.updateSettings(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    @Override
    public ResponseEntity<Boolean> deleteSettings(@PathVariable(value = "id") Long id) {
        return settingsService.deleteSettings(id);
    }
}
