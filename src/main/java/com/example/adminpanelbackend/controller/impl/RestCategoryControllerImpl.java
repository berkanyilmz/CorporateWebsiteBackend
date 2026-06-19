package com.example.adminpanelbackend.controller.impl;

import com.example.adminpanelbackend.controller.IRestCategoryController;
import com.example.adminpanelbackend.dto.Product.CategoryDto;
import com.example.adminpanelbackend.dto.Product.CategoryDtoUI;
import com.example.adminpanelbackend.services.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class RestCategoryControllerImpl implements IRestCategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'VIEWER')")
    @Override
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'VIEWER')")
    @Override
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(value = "id") Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @Override
    public ResponseEntity<Boolean> addCategory(@Valid @RequestBody CategoryDtoUI dto) {
        return categoryService.createCategory(dto);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @Override
    public ResponseEntity<Boolean> updateCategory(@PathVariable(value = "id") Long id, @Valid @RequestBody CategoryDtoUI dto) {
        return categoryService.updateCategory(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<Boolean> softDeleteCategory(@PathVariable(value = "id") Long id) {
        return categoryService.softDeleteCategory(id);
    }

    @DeleteMapping("/delete/{id}/hard")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<Boolean> hardDeleteCategory(@PathVariable(value = "id") Long id) {
        return categoryService.hardDeleteCategory(id);
    }
}
