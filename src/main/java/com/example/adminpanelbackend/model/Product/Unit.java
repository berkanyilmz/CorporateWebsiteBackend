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
public class Unit extends BaseModel {

    @Column(name = "unit_name", nullable = false, unique = true, length = 50)
    private String unitName;

    @Column(nullable = false)
    private boolean deleted = false;

}
