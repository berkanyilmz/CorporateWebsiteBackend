package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseModel {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String categoriContext;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = true)
    private int parentID;


}
