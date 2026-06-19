package com.example.adminpanelbackend.model.User;


import com.example.adminpanelbackend.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Roles extends BaseModel {

    @Column(unique = true)
    private String roleName;

    @Column
    private String roleDescription;

}
