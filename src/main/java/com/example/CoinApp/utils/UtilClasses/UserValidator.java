package com.example.CoinApp.utils.UtilClasses;

import com.example.CoinApp.errors.UserValidationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Component
@Validated
public class UserValidator {

    private static final int MAX_PASSWORD_LENGTH = 128;
    private static final int MAX_USERNAME_LENGTH = 10;

    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username can only contain alphanumeric characters")
    public boolean validateUsername(@NotBlank(message = "Username cannot be blank") String username) {
        return username.length() <= MAX_USERNAME_LENGTH;
    }

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$", message = "Invalid email format")
    public boolean validateEmail(@NotBlank(message = "Email cannot be blank") String email) {
        return true;
    }

    public boolean validatePassword(@NotBlank(message = "Password cannot be blank") String password, String username) {
        if (password.length() > MAX_PASSWORD_LENGTH) {
            return false;
        }
        if (password.contains(" ")) {
            return false;
        }
        if (username != null && password.contains(username)) {
            return false;
        }
        return true;
    }

    public boolean validateCountryOfResidence(@NotBlank(message = "Country of residence cannot be blank") String countryOfResidence) {
        return true;
    }
}