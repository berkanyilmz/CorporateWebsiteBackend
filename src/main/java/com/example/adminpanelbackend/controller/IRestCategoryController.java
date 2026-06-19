package com.example.adminpanelbackend.controller;

import com.example.adminpanelbackend.dto.Product.CategoryDto;
import com.example.adminpanelbackend.dto.Product.CategoryDtoUI;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IRestCategoryController {

    ResponseEntity<List<CategoryDto>> getAllCategories();

    ResponseEntity<CategoryDto> getCategoryById(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> addCategory(@Valid @RequestBody CategoryDtoUI dto);

    ResponseEntity<Boolean> updateCategory(@PathVariable(value = "id") Long id, @Valid @RequestBody CategoryDtoUI dto);

    ResponseEntity<Boolean> softDeleteCategory(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> hardDeleteCategory(@PathVariable(value = "id") Long id);
}
