package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Product.ProductImageDto;
import com.example.adminpanelbackend.dto.Product.ProductImageDtoUI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductImageService {

    ResponseEntity<List<ProductImageDto>> getImagesByProductId(Long productId);

    ResponseEntity<ProductImageDto> getImageById(Long id);

    ResponseEntity<Boolean> addImageToProduct(Long productId, ProductImageDtoUI dto, MultipartFile file);

    ResponseEntity<Boolean> updateImage(Long productId, Long imageId, ProductImageDtoUI dto, MultipartFile file);

    ResponseEntity<Boolean> deleteImage(Long productId, Long imageId);
}
