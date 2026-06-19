package com.example.adminpanelbackend.model.JWT;

import com.example.adminpanelbackend.model.BaseModel;
import com.example.adminpanelbackend.model.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "refresh_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RefreshToken extends BaseModel {

    @Column
    private String refreshToken;

    @Column
    private Date expireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
