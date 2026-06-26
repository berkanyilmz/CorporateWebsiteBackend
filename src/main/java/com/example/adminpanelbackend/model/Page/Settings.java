package com.example.adminpanelbackend.model.Page;

import com.example.adminpanelbackend.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Settings extends BaseModel {

    @Column
    private String instagramAddress;

    @Column
    private String linkedinAddress;

    @Column
    private String facebookAddress;

    @Column
    private String twitterAddress;

    @Column
    private String youtubeAddress;

    @Column
    private String tiktokAddress;

    @Column
    private String whatsappAddress;

    @Column
    private String telegramAddress;

    @Column
    private String address;

    @Column(columnDefinition = "TEXT")
    private String googleMap;

    @Column
    private String email;

    @Column
    private String phone;

    @Column(name = "header_logo_id")
    private String headerLogo;

    @Column(name = "footer_logo_id")
    private String footerLogo;

    @Column(name = "favicon_logo_id")
    private String favicon;

    @Column
    private String companyName;

}
