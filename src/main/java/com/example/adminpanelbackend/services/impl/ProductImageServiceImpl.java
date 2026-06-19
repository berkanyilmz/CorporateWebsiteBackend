package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.Product.ProductImageDto;
import com.example.adminpanelbackend.dto.Product.ProductImageDtoUI;
import com.example.adminpanelbackend.model.Product.ProductImage;
import com.example.adminpanelbackend.model.Product.Products;
import com.example.adminpanelbackend.repository.IProductImageRepository;
import com.example.adminpanelbackend.repository.IProductRepository;
import com.example.adminpanelbackend.services.FileStorageService;
import com.example.adminpanelbackend.services.IProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImpl implements IProductImageService {

    @Autowired
    private IProductImageRepository productImageRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public ResponseEntity<List<ProductImageDto>> getImagesByProductId(Long productId) {
        if (!productRepository.existsById(productId)) {
            return ResponseEntity.notFound().build();
        }
        List<ProductImage> images = productImageRepository.findAllByProductId(productId);
        List<ProductImageDto> dtos = images.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<ProductImageDto> getImageById(Long id) {
        Optional<ProductImage> image = productImageRepository.findById(id);
        if (image.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapToDto(image.get()));
    }

    @Override
    public ResponseEntity<Boolean> addImageToProduct(Long productId, ProductImageDtoUI dto, MultipartFile file) {
        Optional<Products> product = productRepository.findByIdAndDeletedFalse(productId);
        if (product.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        String imagePath = resolveImagePath(productId, dto, file, null);
        if (imagePath == null || imagePath.isBlank()) {
            return ResponseEntity.badRequest().body(false);
        }

        if (dto.isMain()) {
            resetMainImage(productId);
        }

        ProductImage image = new ProductImage();
        image.setProduct(product.get());
        image.setImagePath(imagePath);
        image.setSortOrder(dto.getSortOrder());
        image.setMain(dto.isMain());
        productImageRepository.save(image);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> updateImage(Long productId, Long imageId, ProductImageDtoUI dto, MultipartFile file) {
        Optional<ProductImage> existing = productImageRepository.findByIdAndProductId(imageId, productId);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        ProductImage image = existing.get();
        String newPath = resolveImagePath(productId, dto, file, image.getImagePath());
        if (newPath != null && !newPath.equals(image.getImagePath())) {
            image.setImagePath(newPath);
        }

        if (dto.isMain() && !image.isMain()) {
            resetMainImage(productId);
        }

        image.setSortOrder(dto.getSortOrder());
        image.setMain(dto.isMain());
        productImageRepository.save(image);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> deleteImage(Long productId, Long imageId) {
        Optional<ProductImage> existing = productImageRepository.findByIdAndProductId(imageId, productId);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        ProductImage image = existing.get();
        fileStorageService.delete(image.getImagePath());
        productImageRepository.delete(image);
        return ResponseEntity.ok(true);
    }

    private String resolveImagePath(Long productId, ProductImageDtoUI dto, MultipartFile file, String currentPath) {
        if (file != null && !file.isEmpty()) {
            if (currentPath != null && !currentPath.isBlank()) {
                fileStorageService.delete(currentPath);
            }
            return fileStorageService.store(file, String.valueOf(productId));
        }
        if (dto.getImagePath() != null && !dto.getImagePath().isBlank()) {
            return dto.getImagePath();
        }
        return currentPath;
    }

    private void resetMainImage(Long productId) {
        Optional<ProductImage> existingMain = productImageRepository.findByProductIdAndIsMainTrue(productId);
        existingMain.ifPresent(img -> {
            img.setMain(false);
            productImageRepository.save(img);
        });
    }

    private ProductImageDto mapToDto(ProductImage image) {
        ProductImageDto dto = new ProductImageDto();
        dto.setId(image.getId());
        dto.setProductId(image.getProduct().getId());
        dto.setImagePath(image.getImagePath());
        dto.setSortOrder(image.getSortOrder());
        dto.setMain(image.isMain());
        return dto;
    }
}
