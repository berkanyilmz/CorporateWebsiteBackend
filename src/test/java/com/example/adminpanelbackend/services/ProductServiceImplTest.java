package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Product.ProductDtoUI;
import com.example.adminpanelbackend.model.Category.Category;
import com.example.adminpanelbackend.model.Product.Products;
import com.example.adminpanelbackend.repository.ICategoryRepository;
import com.example.adminpanelbackend.repository.IProductRepository;
import com.example.adminpanelbackend.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private ICategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createProduct_shouldReturnTrue_whenValidInput() {
        ProductDtoUI dto = new ProductDtoUI();
        dto.setProductName("Test Product");
        dto.setProductDescription("Description");
        dto.setActive(true);
        dto.setCategoryId(1L);

        Category category = new Category();
        category.setId(1L);
        category.setCategoryName("Test Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.existsBySlugAndDeletedFalse(anyString())).thenReturn(false);

        ResponseEntity<Boolean> response = productService.createProduct(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(productRepository).save(any(Products.class));
    }

    @Test
    void createProduct_shouldReturnBadRequest_whenCategoryNotFound() {
        ProductDtoUI dto = new ProductDtoUI();
        dto.setProductName("Test Product");
        dto.setProductDescription("Description");
        dto.setActive(true);
        dto.setCategoryId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Boolean> response = productService.createProduct(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody());
        verify(productRepository, never()).save(any(Products.class));
    }

    @Test
    void createProduct_shouldReturnBadRequest_whenSlugAlreadyExists() {
        ProductDtoUI dto = new ProductDtoUI();
        dto.setProductName("Existing Product");
        dto.setSlug("existing-product");
        dto.setProductDescription("Description");
        dto.setActive(true);
        dto.setCategoryId(1L);

        Category category = new Category();
        category.setId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.existsBySlugAndDeletedFalse("existing-product")).thenReturn(true);

        ResponseEntity<Boolean> response = productService.createProduct(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody());
        verify(productRepository, never()).save(any(Products.class));
    }
}
