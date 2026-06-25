package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.Page.SettingsDto;
import com.example.adminpanelbackend.dto.Page.SettingsDtoUI;
import com.example.adminpanelbackend.model.Page.Settings;
import com.example.adminpanelbackend.repository.ISettingsRepository;
import com.example.adminpanelbackend.services.ISettingsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SettingsServiceImpl implements ISettingsService {

    @Autowired
    private ISettingsRepository settingsRepository;

    @Override
    public ResponseEntity<List<SettingsDto>> getAllSettings() {
        List<Settings> settingsList = settingsRepository.findAll();
        if (settingsList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<SettingsDto> dtos = settingsList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<SettingsDto> getSettingsById(Long id) {
        Optional<Settings> settings = settingsRepository.findById(id);
        if (settings.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapToDto(settings.get()));
    }

    @Override
    public ResponseEntity<Boolean> createSettings(SettingsDtoUI dto) {
        Settings settings = new Settings();
        BeanUtils.copyProperties(dto, settings);
        LocalDateTime now = LocalDateTime.now();
        settings.setCreatedAt(now);
        settings.setUpdatedAt(now);
        settingsRepository.save(settings);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> updateSettings(Long id, SettingsDtoUI dto) {
        Optional<Settings> existing = settingsRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        Settings settings = existing.get();
        BeanUtils.copyProperties(dto, settings);
        settings.setUpdatedAt(LocalDateTime.now());
        settingsRepository.save(settings);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> deleteSettings(Long id) {
        if (!settingsRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(false);
        }
        settingsRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }

    private SettingsDto mapToDto(Settings settings) {
        SettingsDto dto = new SettingsDto();
        BeanUtils.copyProperties(settings, dto);
        if (settings.getCreatedAt() != null) {
            dto.setCreatedAt(Date.from(settings.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant()));
        }
        if (settings.getUpdatedAt() != null) {
            dto.setUpdatedDate(Date.from(settings.getUpdatedAt().atZone(ZoneId.systemDefault()).toInstant()));
        }
        return dto;
    }
}
