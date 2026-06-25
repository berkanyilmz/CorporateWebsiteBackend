package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Page.SettingsDto;
import com.example.adminpanelbackend.dto.Page.SettingsDtoUI;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ISettingsService {

    ResponseEntity<List<SettingsDto>> getAllSettings();

    ResponseEntity<SettingsDto> getSettingsById(Long id);

    ResponseEntity<Boolean> createSettings(SettingsDtoUI dto);

    ResponseEntity<Boolean> updateSettings(Long id, SettingsDtoUI dto);

    ResponseEntity<Boolean> deleteSettings(Long id);
}
