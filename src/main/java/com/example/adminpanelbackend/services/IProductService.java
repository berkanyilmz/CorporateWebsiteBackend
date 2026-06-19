package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Product.ProductDto;
import com.example.adminpanelbackend.dto.Product.ProductDtoUI;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProductService {

    ResponseEntity<List<ProductDto>> getAllProducts();

    ResponseEntity<ProductDto> getProductById(Long id);

    ResponseEntity<Boolean> createProduct(ProductDtoUI dto);

    ResponseEntity<Boolean> updateProduct(Long id, ProductDtoUI dto);

    ResponseEntity<Boolean> softDeleteProduct(Long id);

    ResponseEntity<Boolean> hardDeleteProduct(Long id);
}
