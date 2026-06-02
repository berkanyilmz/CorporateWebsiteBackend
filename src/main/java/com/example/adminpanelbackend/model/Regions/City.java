package com.example.adminpanelbackend.model.Regions;

import com.example.adminpanelbackend.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Cities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City extends BaseModel {

    @Column(nullable = false)
    private String cityName;

}
