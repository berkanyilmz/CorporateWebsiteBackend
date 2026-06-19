package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Product.CategoryDto;
import com.example.adminpanelbackend.dto.Product.CategoryDtoUI;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICategoryService {

    ResponseEntity<List<CategoryDto>> getAllCategories();

    ResponseEntity<CategoryDto> getCategoryById(Long id);

    ResponseEntity<Boolean> createCategory(CategoryDtoUI dto);

    ResponseEntity<Boolean> updateCategory(Long id, CategoryDtoUI dto);

    ResponseEntity<Boolean> softDeleteCategory(Long id);

    ResponseEntity<Boolean> hardDeleteCategory(Long id);
}
