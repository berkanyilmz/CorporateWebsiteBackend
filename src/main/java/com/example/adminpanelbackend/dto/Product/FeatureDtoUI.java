package com.example.adminpanelbackend.dto.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureDtoUI {

    @NotBlank(message = "Feature name cannot be empty")
    @Size(max = 100, message = "Feature name must be at most 100 characters")
    private String featureName;
}
