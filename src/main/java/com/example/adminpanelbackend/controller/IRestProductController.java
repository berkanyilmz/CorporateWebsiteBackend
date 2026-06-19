package com.example.adminpanelbackend.controller;

import com.example.adminpanelbackend.dto.Product.ProductDto;
import com.example.adminpanelbackend.dto.Product.ProductDtoUI;
import com.example.adminpanelbackend.dto.Product.ProductFeatureDto;
import com.example.adminpanelbackend.dto.Product.ProductFeatureDtoUI;
import com.example.adminpanelbackend.dto.Product.ProductImageDto;
import com.example.adminpanelbackend.dto.Product.ProductImageDtoUI;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRestProductController {

    ResponseEntity<List<ProductDto>> getAllProducts();

    ResponseEntity<ProductDto> getProductById(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> addProduct(@Valid @RequestBody ProductDtoUI dto);

    ResponseEntity<Boolean> updateProduct(@PathVariable(value = "id") Long id, @Valid @RequestBody ProductDtoUI dto);

    ResponseEntity<Boolean> softDeleteProduct(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> hardDeleteProduct(@PathVariable(value = "id") Long id);

    ResponseEntity<List<ProductFeatureDto>> getFeaturesByProductId(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> addFeatureToProduct(@PathVariable(value = "id") Long id, @Valid @RequestBody ProductFeatureDtoUI dto);

    ResponseEntity<Boolean> updateFeature(@PathVariable(value = "id") Long id, @PathVariable(value = "featureId") Long featureId, @Valid @RequestBody ProductFeatureDtoUI dto);

    ResponseEntity<Boolean> deleteFeature(@PathVariable(value = "id") Long id, @PathVariable(value = "featureId") Long featureId);

    ResponseEntity<List<ProductImageDto>> getImagesByProductId(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> addImageToProduct(@PathVariable(value = "id") Long id, @ModelAttribute ProductImageDtoUI dto, @RequestParam(value = "file", required = false) MultipartFile file);

    ResponseEntity<Boolean> updateImage(@PathVariable(value = "id") Long id, @PathVariable(value = "imageId") Long imageId, @ModelAttribute ProductImageDtoUI dto, @RequestParam(value = "file", required = false) MultipartFile file);

    ResponseEntity<Boolean> deleteImage(@PathVariable(value = "id") Long id, @PathVariable(value = "imageId") Long imageId);
}
