package com.example.adminpanelbackend.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFeatureDto {

    private Long id;

    private Long productId;

    private Long featureId;

    private String featureName;

    private Long unitId;

    private String unitName;

    private String featureValue;
}
