package com.example.adminpanelbackend.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureDto {

    private Long id;

    private String featureName;

    private boolean deleted;
}
