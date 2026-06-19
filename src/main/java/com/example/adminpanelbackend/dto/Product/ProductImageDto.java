package com.example.adminpanelbackend.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDto {

    private Long id;

    private Long productId;

    private String imagePath;

    private int sortOrder;

    private boolean main;
}
