package com.example.adminpanelbackend.model.Category;

import com.example.adminpanelbackend.model.BaseModel;
import com.example.adminpanelbackend.model.Product.Products;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseModel {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 100, unique = true)
    private String categoryName;

    @Column(nullable = false, length = 100, unique = true)
    private String slug;

    @Column(nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Products> products;

}
