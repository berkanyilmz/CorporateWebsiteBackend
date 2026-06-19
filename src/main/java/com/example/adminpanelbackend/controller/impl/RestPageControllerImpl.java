package com.example.adminpanelbackend.controller.impl;

import com.example.adminpanelbackend.controller.IRestPageController;
import com.example.adminpanelbackend.dto.Page.PageDto;
import com.example.adminpanelbackend.dto.Page.PageDtoUI;
import com.example.adminpanelbackend.services.IPageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pages")
public class RestPageControllerImpl implements IRestPageController {

    @Autowired
    private IPageService pageService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'VIEWER')")
    @Override
    public ResponseEntity<List<PageDto>> getAllPages() {
        return pageService.getAllPages();
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'VIEWER')")
    @Override
    public ResponseEntity<PageDto> getPageById(@PathVariable(value = "id") Long id) {
        return pageService.getPageById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @Override
    public ResponseEntity<Boolean> addPage(@Valid @RequestBody PageDtoUI dto) {
        return pageService.createPage(dto);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @Override
    public ResponseEntity<Boolean> updatePage(@PathVariable(value = "id") Long id, @Valid @RequestBody PageDtoUI dto) {
        return pageService.updatePage(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<Boolean> softDeletePage(@PathVariable(value = "id") Long id) {
        return pageService.softDeletePage(id);
    }

    @DeleteMapping("/delete/{id}/hard")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<Boolean> hardDeletePage(@PathVariable(value = "id") Long id) {
        return pageService.hardDeletePage(id);
    }
}
