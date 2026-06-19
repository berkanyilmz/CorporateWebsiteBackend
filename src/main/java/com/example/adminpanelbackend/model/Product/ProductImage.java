package com.example.adminpanelbackend.model.Product;

import com.example.adminpanelbackend.model.BaseModel;
import jakarta.persistence.*;
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
public class ProductImage extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @Column
    private String imagePath;

    @Column(nullable = false)
    private int sortOrder = 0;

    @Column
    private boolean isMain = false;

}
