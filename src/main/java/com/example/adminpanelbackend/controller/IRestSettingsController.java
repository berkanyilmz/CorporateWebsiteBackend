package com.example.adminpanelbackend.controller;

import com.example.adminpanelbackend.dto.Page.SettingsDto;
import com.example.adminpanelbackend.dto.Page.SettingsDtoUI;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IRestSettingsController {

    ResponseEntity<List<SettingsDto>> getAllSettings();

    ResponseEntity<SettingsDto> getSettingsById(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> addSettings(@Valid @RequestBody SettingsDtoUI dto);

    ResponseEntity<Boolean> updateSettings(@PathVariable(value = "id") Long id, @Valid @RequestBody SettingsDtoUI dto);

    ResponseEntity<Boolean> deleteSettings(@PathVariable(value = "id") Long id);
}
