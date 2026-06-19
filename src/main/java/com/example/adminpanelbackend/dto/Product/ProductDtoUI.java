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
public class ProductDtoUI {

    @NotBlank(message = "Product name cannot be empty")
    private String productName;

    @Size(max = 200, message = "Slug must be at most 200 characters")
    private String slug;

    @NotBlank(message = "Product description cannot be empty")
    private String productDescription;

    @NotNull(message = "Active status is required")
    private Boolean active;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}
