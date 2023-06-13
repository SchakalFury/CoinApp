package com.example.CoinApp.dto;

import com.example.CoinApp.models.Currency;
import com.example.CoinApp.models.User;
import com.example.CoinApp.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean isEnabled;
    private LocalDate registrationDate;
    private String countryOfResidence;
    private UserRole userRole;
    private List<CurrencyDTO> currencies;



    public static UserDTO fromEntity(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setEnabled(user.isEnabled());
        userDTO.setRegistrationDate(user.getRegistrationDate());
        userDTO.setCountryOfResidence(user.getCountryOfResidence());
        userDTO.setUserRole(user.getUserRole());
        userDTO.setCurrencies(CurrencyDTO.fromEntities(user.getCurrencies()));
        return userDTO;
    }

    public User toEntity() {
        User user = new User();
        user.setId(this.getId());
        user.setUsername(this.getUsername());
        user.setPassword(this.getPassword());
        user.setEmail(this.getEmail());
        user.setEnabled(this.isEnabled());
        user.setRegistrationDate(this.getRegistrationDate());
        user.setCountryOfResidence(this.getCountryOfResidence());
        user.setUserRole(this.getUserRole());
        user.setCurrencies(CurrencyDTO.toEntities(this.getCurrencies()));
        return user;
    }

    public static List<User> toEntities(List<UserDTO> userDTOs) {
        return userDTOs.stream()
                .map(UserDTO::toEntity)
                .collect(Collectors.toList());
    }

    public static List<UserDTO> fromEntities(List<User> users) {
        return users.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }
}


