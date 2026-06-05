package com.example.adminpanelbackend.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserRegisterDto {

    @NotEmpty(message = "First Name Not Empty")
    private String userFirstName;

    @NotEmpty(message = "Last Name Not Empty")
    private String userLastName;

    @Email
    private String userEmail;

    @Length(min = 8, message = "Parola en az 8 karakter olmalıdır.")
    private String password;

}
