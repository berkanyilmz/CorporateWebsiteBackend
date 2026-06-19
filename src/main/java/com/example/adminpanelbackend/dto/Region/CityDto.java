package com.example.adminpanelbackend.dto.Region;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDto {

    private Long id;

    private String cityName;

    private boolean deleted;
}
