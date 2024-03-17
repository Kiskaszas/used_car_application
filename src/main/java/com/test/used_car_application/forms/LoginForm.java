package com.test.used_car_application.forms;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm {
    @NotBlank(message = "A felhasználónév nem lehet üres")
    private String username;

    @NotBlank(message = "A jelszó nem lehet üres")
    private String password;
}