package com.example.adminpanelbackend.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDtoUI {

    @NotEmpty(message = "First Name Not Empty")
    private String userFirstName;

    @NotEmpty(message = "Last Name Not Empty")
    private String userLastName;

    @Email
    private String userEmail;

    @Length(min = 8, message = "Parola en az 8 karakter olmalıdır.")
    private String password;

    @NotNull(message = "Rol seçimi zorunludur")
    private Long roleId;

}
