package com.example.adminpanelbackend.model.User;

import com.example.adminpanelbackend.model.BaseModel;
import com.example.adminpanelbackend.model.Regions.Country;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
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
    @Email
    private String userEmail;

    @Column
    private String passwordHash;

    @Column
    private boolean deleted;

    @Column
    private Date createdDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = true)
    private Roles roleID;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roleID == null || roleID.getRoleName() == null || roleID.getRoleName().isBlank()) {
            return List.of();
        }
        String roleName = roleID.getRoleName().startsWith("ROLE_")
                ? roleID.getRoleName()
                : "ROLE_" + roleID.getRoleName();
        return List.of(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.userEmail;
    }
}
