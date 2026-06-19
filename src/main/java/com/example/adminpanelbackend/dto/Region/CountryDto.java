package com.example.adminpanelbackend.dto.Region;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {

    private Long id;

    private String countryName;

    private int countryCode;

    private boolean deleted;
}
