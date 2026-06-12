package com.example.adminpanelbackend.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String userFirstName;

    private String userLastName;

    private String userPhoneNumber;

    private String userEmail;

    private boolean deleted;

    private Date createdDate;

}
