package com.example.adminpanelbackend.dto.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SliderDto {

    private Long id;

    private Long imageId;

    private String imagePath;

    private Long productId;

    private String productName;

    private String title;

    private int sortOrder;

    private boolean active;
}
