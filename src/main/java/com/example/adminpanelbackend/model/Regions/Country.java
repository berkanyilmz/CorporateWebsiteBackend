package com.example.adminpanelbackend.model.Regions;

import com.example.adminpanelbackend.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Country extends BaseModel {

    @Column
    private String countryName;

    @Column(length = 4)
    private int countryCode;

    @Column(nullable = false)
    private boolean deleted = false;

}
