package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.Product.CategoryDto;
import com.example.adminpanelbackend.dto.Product.CategoryDtoUI;
import com.example.adminpanelbackend.model.Category.Category;
import com.example.adminpanelbackend.repository.ICategoryRepository;
import com.example.adminpanelbackend.services.ICategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> categories = categoryRepository.findAllByDeletedFalse();
        if (categories.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<CategoryDto> dtos = categories.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<CategoryDto> getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findByIdAndDeletedFalse(id);
        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapToDto(category.get()));
    }

    @Override
    public ResponseEntity<Boolean> createCategory(CategoryDtoUI dto) {
        if (categoryRepository.existsByCategoryNameAndDeletedFalse(dto.getCategoryName())) {
            return ResponseEntity.badRequest().body(false);
        }

        String slug = dto.getSlug();
        if (slug == null || slug.isBlank()) {
            slug = generateSlug(dto.getCategoryName());
        } else {
            slug = slug.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", "-");
        }
        if (categoryRepository.existsBySlugAndDeletedFalse(slug)) {
            return ResponseEntity.badRequest().body(false);
        }

        Category category = new Category();
        category.setCategoryName(dto.getCategoryName());
        category.setSlug(slug);
        category.setActive(dto.getActive());
        category.setDeleted(false);
        categoryRepository.save(category);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> updateCategory(Long id, CategoryDtoUI dto) {
        Optional<Category> existing = categoryRepository.findByIdAndDeletedFalse(id);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        if (categoryRepository.existsByCategoryNameAndDeletedFalseAndIdNot(dto.getCategoryName(), id)) {
            return ResponseEntity.badRequest().body(false);
        }

        Category category = existing.get();
        String slug = dto.getSlug();
        if (slug != null && !slug.isBlank()) {
            slug = slug.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", "-");
            if (!slug.equals(category.getSlug()) && categoryRepository.existsBySlugAndDeletedFalseAndIdNot(slug, id)) {
                return ResponseEntity.badRequest().body(false);
            }
            category.setSlug(slug);
        }

        category.setCategoryName(dto.getCategoryName());
        category.setActive(dto.getActive());
        categoryRepository.save(category);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> softDeleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findByIdAndDeletedFalse(id);
        if (category.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        category.get().setDeleted(true);
        categoryRepository.save(category.get());
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> hardDeleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(false);
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }

    private CategoryDto mapToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        BeanUtils.copyProperties(category, dto);
        dto.setActive(category.isActive());
        return dto;
    }

    private String generateSlug(String input) {
        String base = input.toLowerCase(Locale.ROOT)
                .replaceAll("[ç]", "c")
                .replaceAll("[ğ]", "g")
                .replaceAll("[ı]", "i")
                .replaceAll("[ö]", "o")
                .replaceAll("[ş]", "s")
                .replaceAll("[ü]", "u")
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
        if (base.isBlank()) {
            base = "category";
        }
        String slug = base;
        int counter = 1;
        while (categoryRepository.existsBySlugAndDeletedFalse(slug)) {
            slug = base + "-" + counter++;
        }
        return slug;
    }
}
