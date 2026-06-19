package com.example.adminpanelbackend.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    private String productName;

    private String slug;

    private String productDescription;

    private boolean active;

    private Long categoryId;

    private String categoryName;

    private Date createdAt;

    private boolean deleted;

    private List<ProductFeatureDto> productFeatures;

    private List<ProductImageDto> productImages;
}
