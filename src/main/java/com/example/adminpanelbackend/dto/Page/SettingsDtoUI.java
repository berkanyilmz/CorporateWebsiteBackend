package com.example.adminpanelbackend.dto.Page;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingsDtoUI {

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

    @Email(message = "Email must be valid")
    private String email;

    private String phone;
}
