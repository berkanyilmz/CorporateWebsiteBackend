package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.Product.ProductDto;
import com.example.adminpanelbackend.dto.Product.ProductDtoUI;
import com.example.adminpanelbackend.model.Category.Category;
import com.example.adminpanelbackend.model.Product.Products;
import com.example.adminpanelbackend.repository.ICategoryRepository;
import com.example.adminpanelbackend.repository.IProductRepository;
import com.example.adminpanelbackend.services.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Products> products = productRepository.findAllByDeletedFalse();
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ProductDto> dtos = products.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<ProductDto> getProductById(Long id) {
        Optional<Products> product = productRepository.findByIdAndDeletedFalse(id);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapToDto(product.get()));
    }

    @Override
    public ResponseEntity<Boolean> createProduct(ProductDtoUI dto) {
        Optional<Category> category = categoryRepository.findById(dto.getCategoryId());
        if (category.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        String slug = dto.getSlug();
        if (slug == null || slug.isBlank()) {
            slug = generateSlug(dto.getProductName());
        } else {
            slug = slug.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", "-");
        }
        if (productRepository.existsBySlugAndDeletedFalse(slug)) {
            return ResponseEntity.badRequest().body(false);
        }

        Products product = new Products();
        product.setProductName(dto.getProductName());
        product.setSlug(slug);
        product.setProductDescription(dto.getProductDescription());
        product.setActive(dto.getActive());
        product.setCategory(category.get());
        product.setCreatedAt(new Date());
        product.setDeleted(false);
        productRepository.save(product);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> updateProduct(Long id, ProductDtoUI dto) {
        Optional<Products> existing = productRepository.findByIdAndDeletedFalse(id);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        Optional<Category> category = categoryRepository.findById(dto.getCategoryId());
        if (category.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        Products product = existing.get();
        String slug = dto.getSlug();
        if (slug != null && !slug.isBlank()) {
            slug = slug.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", "-");
            if (!slug.equals(product.getSlug()) && productRepository.existsBySlugAndDeletedFalseAndIdNot(slug, id)) {
                return ResponseEntity.badRequest().body(false);
            }
            product.setSlug(slug);
        }

        product.setProductName(dto.getProductName());
        product.setProductDescription(dto.getProductDescription());
        product.setActive(dto.getActive());
        product.setCategory(category.get());
        productRepository.save(product);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> softDeleteProduct(Long id) {
        Optional<Products> product = productRepository.findByIdAndDeletedFalse(id);
        if (product.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        product.get().setDeleted(true);
        productRepository.save(product.get());
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> hardDeleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(false);
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }

    private ProductDto mapToDto(Products product) {
        ProductDto dto = new ProductDto();
        BeanUtils.copyProperties(product, dto);
        dto.setActive(product.isActive());
        dto.setCategoryId(product.getCategory().getId());
        dto.setCategoryName(product.getCategory().getCategoryName());
        dto.setProductFeatures(product.getProductFeatures() != null
                ? product.getProductFeatures().stream().map(feature -> {
                    com.example.adminpanelbackend.dto.Product.ProductFeatureDto featureDto = new com.example.adminpanelbackend.dto.Product.ProductFeatureDto();
                    featureDto.setId(feature.getId());
                    featureDto.setProductId(product.getId());
                    featureDto.setFeatureId(feature.getFeature().getId());
                    featureDto.setFeatureName(feature.getFeature().getFeatureName());
                    if (feature.getUnit() != null) {
                        featureDto.setUnitId(feature.getUnit().getId());
                        featureDto.setUnitName(feature.getUnit().getUnitName());
                    }
                    featureDto.setFeatureValue(feature.getFeatureValue());
                    return featureDto;
                }).collect(Collectors.toList())
                : Collections.emptyList());
        dto.setProductImages(product.getProductImages() != null
                ? product.getProductImages().stream().map(image -> {
                    com.example.adminpanelbackend.dto.Product.ProductImageDto imageDto = new com.example.adminpanelbackend.dto.Product.ProductImageDto();
                    imageDto.setId(image.getId());
                    imageDto.setProductId(product.getId());
                    imageDto.setImagePath(image.getImagePath());
                    imageDto.setSortOrder(image.getSortOrder());
                    imageDto.setMain(image.isMain());
                    return imageDto;
                }).collect(Collectors.toList())
                : Collections.emptyList());
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
            base = "product";
        }
        String slug = base;
        int counter = 1;
        while (productRepository.existsBySlugAndDeletedFalse(slug)) {
            slug = base + "-" + counter++;
        }
        return slug;
    }
}
