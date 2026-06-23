package com.example.adminpanelbackend.model.Image;

import com.example.adminpanelbackend.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Image extends BaseModel {

    @Column
    private String imageName;

    @Column
    private String imagePath;

    @Column
    private String altText;

}
