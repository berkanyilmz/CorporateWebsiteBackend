package com.example.adminpanelbackend.dto.Region;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDtoUI {

    @NotBlank(message = "City name cannot be empty")
    private String cityName;
}
