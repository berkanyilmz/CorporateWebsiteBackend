package com.example.adminpanelbackend.model.Product;

import com.example.adminpanelbackend.model.BaseModel;
import com.example.adminpanelbackend.model.Category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Products")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Products extends BaseModel {

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String productDescription;

    @Column(nullable = false)
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Category category;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private boolean deleted = false;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductFeature> productFeatures;
}
