package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Product.CategoryDtoUI;
import com.example.adminpanelbackend.model.Category.Category;
import com.example.adminpanelbackend.repository.ICategoryRepository;
import com.example.adminpanelbackend.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void createCategory_shouldReturnTrue_whenCategoryNameIsUnique() {
        CategoryDtoUI dto = new CategoryDtoUI();
        dto.setName("Electronics");
        dto.setCategoryName("Electronics");
        dto.setActive(true);

        when(categoryRepository.existsByCategoryNameAndDeletedFalse("Electronics")).thenReturn(false);
        when(categoryRepository.existsBySlugAndDeletedFalse(anyString())).thenReturn(false);

        ResponseEntity<Boolean> response = categoryService.createCategory(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void createCategory_shouldReturnBadRequest_whenCategoryNameAlreadyExists() {
        CategoryDtoUI dto = new CategoryDtoUI();
        dto.setName("Electronics");
        dto.setCategoryName("Electronics");
        dto.setActive(true);

        when(categoryRepository.existsByCategoryNameAndDeletedFalse("Electronics")).thenReturn(true);

        ResponseEntity<Boolean> response = categoryService.createCategory(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody());
        verify(categoryRepository, never()).save(any(Category.class));
    }
}
