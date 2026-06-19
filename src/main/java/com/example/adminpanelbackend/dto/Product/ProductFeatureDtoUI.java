package com.example.adminpanelbackend.dto.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFeatureDtoUI {

    @NotNull(message = "Feature ID is required")
    private Long featureId;

    private Long unitId;

    @NotBlank(message = "Feature value cannot be empty")
    @Size(max = 255, message = "Feature value must be at most 255 characters")
    private String featureValue;
}
