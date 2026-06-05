package com.example.adminpanelbackend.model.JWT;

import com.example.adminpanelbackend.model.BaseModel;
import com.example.adminpanelbackend.model.User.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "refresh_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken extends BaseModel {

    @Column
    private String refreshToken;

    @Column
    private Date expireDate;

    @ManyToOne
    private User user;

}
