package com.example.adminpanelbackend.controller;

import com.example.adminpanelbackend.dto.Page.PageDto;
import com.example.adminpanelbackend.dto.Page.PageDtoUI;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IRestPageController {

    ResponseEntity<List<PageDto>> getAllPages();

    ResponseEntity<PageDto> getPageById(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> addPage(@Valid @RequestBody PageDtoUI dto);

    ResponseEntity<Boolean> updatePage(@PathVariable(value = "id") Long id, @Valid @RequestBody PageDtoUI dto);

    ResponseEntity<Boolean> softDeletePage(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> hardDeletePage(@PathVariable(value = "id") Long id);
}
