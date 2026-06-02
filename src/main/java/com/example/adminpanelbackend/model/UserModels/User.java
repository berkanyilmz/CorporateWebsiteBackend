package com.example.adminpanelbackend.model.UserModels;

import com.example.adminpanelbackend.model.BaseModel;
import com.example.adminpanelbackend.model.Regions.Country;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel implements UserDetails {

    @Column
    private String userFirstName;

    @Column
    private String userLastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Country countryId;

    @Column(length = 10, nullable = true)
    private String userPhoneNumber;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column
    private String passwordHash;

    @Column
    private boolean deleted;

    @Column
    private Date createdDate;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Roles roleID;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
