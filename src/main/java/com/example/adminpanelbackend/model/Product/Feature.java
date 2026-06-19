package com.example.adminpanelbackend.model.Product;

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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Feature extends BaseModel {

    @Column(name = "feature_name", nullable = false, unique = true, length = 100)
    private String featureName;

    @Column(nullable = false)
    private boolean deleted = false;

}
