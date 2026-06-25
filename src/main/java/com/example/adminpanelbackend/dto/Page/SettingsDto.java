package com.example.adminpanelbackend.dto.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingsDto {

    private Long id;

    private String instagramAddress;

    private String linkedinAddress;

    private String facebookAddress;

    private String twitterAddress;

    private String youtubeAddress;

    private String tiktokAddress;

    private String whatsappAddress;

    private String telegramAddress;

    private String address;

    private String googleMap;

    private String email;

    private String phone;

    private Date createdAt;

    private Date updatedDate;
}
