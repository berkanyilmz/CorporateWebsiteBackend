package com.example.adminpanelbackend.dto.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDtoUI {

    @NotBlank(message = "Unit name cannot be empty")
    @Size(max = 50, message = "Unit name must be at most 50 characters")
    private String unitName;
}
