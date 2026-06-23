package com.example.adminpanelbackend.dto.Page;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SliderDtoUI {

    @NotNull
    private Long imageId;

    @NotNull
    private Long productId;

    @NotNull
    private String title;

    private int sortOrder;

    private boolean active = true;
}
