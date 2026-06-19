package com.example.adminpanelbackend.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDtoUI {

    private String imagePath;

    private int sortOrder;

    private boolean main;
}
