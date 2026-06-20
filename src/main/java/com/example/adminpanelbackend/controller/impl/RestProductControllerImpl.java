package com.example.adminpanelbackend.controller.impl;

import com.example.adminpanelbackend.controller.IRestProductController;
import com.example.adminpanelbackend.dto.Product.ProductDto;
import com.example.adminpanelbackend.dto.Product.ProductDtoUI;
import com.example.adminpanelbackend.dto.Product.ProductFeatureDto;
import com.example.adminpanelbackend.dto.Product.ProductFeatureDtoUI;
import com.example.adminpanelbackend.dto.Product.ProductImageDto;
import com.example.adminpanelbackend.dto.Product.ProductImageDtoUI;
import com.example.adminpanelbackend.services.IProductFeatureService;
import com.example.adminpanelbackend.services.IProductImageService;
import com.example.adminpanelbackend.services.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
public class RestProductControllerImpl implements IRestProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductFeatureService productFeatureService;

    @Autowired
    private IProductImageService productImageService;

    @GetMapping("/all")
    @Override
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/get/{id}")
    @Override
    public ResponseEntity<ProductDto> getProductById(@PathVariable(value = "id") Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('Admin', 'Editor')")
    @Override
    public ResponseEntity<Boolean> addProduct(@Valid @RequestBody ProductDtoUI dto) {
        return productService.createProduct(dto);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Editor')")
    @Override
    public ResponseEntity<Boolean> updateProduct(@PathVariable(value = "id") Long id, @Valid @RequestBody ProductDtoUI dto) {
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    @Override
    public ResponseEntity<Boolean> softDeleteProduct(@PathVariable(value = "id") Long id) {
        return productService.softDeleteProduct(id);
    }

    @DeleteMapping("/delete/{id}/hard")
    @PreAuthorize("hasRole('Admin')")
    @Override
    public ResponseEntity<Boolean> hardDeleteProduct(@PathVariable(value = "id") Long id) {
        return productService.hardDeleteProduct(id);
    }

    @GetMapping("/{id}/features")
    @Override
    public ResponseEntity<List<ProductFeatureDto>> getFeaturesByProductId(@PathVariable(value = "id") Long id) {
        return productFeatureService.getFeaturesByProductId(id);
    }

    @PostMapping("/{id}/features/add")
    @PreAuthorize("hasAnyRole('Admin', 'Editor')")
    @Override
    public ResponseEntity<Boolean> addFeatureToProduct(@PathVariable(value = "id") Long id, @Valid @RequestBody ProductFeatureDtoUI dto) {
        return productFeatureService.addFeatureToProduct(id, dto);
    }

    @PutMapping("/{id}/features/update/{featureId}")
    @PreAuthorize("hasAnyRole('Admin', 'Editor')")
    @Override
    public ResponseEntity<Boolean> updateFeature(@PathVariable(value = "id") Long id, @PathVariable(value = "featureId") Long featureId, @Valid @RequestBody ProductFeatureDtoUI dto) {
        return productFeatureService.updateFeature(id, featureId, dto);
    }

    @DeleteMapping("/{id}/features/delete/{featureId}")
    @PreAuthorize("hasRole('Admin')")
    @Override
    public ResponseEntity<Boolean> deleteFeature(@PathVariable(value = "id") Long id, @PathVariable(value = "featureId") Long featureId) {
        return productFeatureService.deleteFeature(id, featureId);
    }

    @GetMapping("/{id}/images")
    @Override
    public ResponseEntity<List<ProductImageDto>> getImagesByProductId(@PathVariable(value = "id") Long id) {
        return productImageService.getImagesByProductId(id);
    }

    @PostMapping(value = "/{id}/images/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('Admin', 'Editor')")
    @Override
    public ResponseEntity<Boolean> addImageToProduct(@PathVariable(value = "id") Long id, @ModelAttribute ProductImageDtoUI dto, @RequestParam(value = "file", required = false) MultipartFile file) {
        return productImageService.addImageToProduct(id, dto, file);
    }

    @PutMapping(value = "/{id}/images/update/{imageId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('Admin', 'Editor')")
    @Override
    public ResponseEntity<Boolean> updateImage(@PathVariable(value = "id") Long id, @PathVariable(value = "imageId") Long imageId, @ModelAttribute ProductImageDtoUI dto, @RequestParam(value = "file", required = false) MultipartFile file) {
        return productImageService.updateImage(id, imageId, dto, file);
    }

    @DeleteMapping("/{id}/images/delete/{imageId}")
    @PreAuthorize("hasRole('Admin')")
    @Override
    public ResponseEntity<Boolean> deleteImage(@PathVariable(value = "id") Long id, @PathVariable(value = "imageId") Long imageId) {
        return productImageService.deleteImage(id, imageId);
    }
}
