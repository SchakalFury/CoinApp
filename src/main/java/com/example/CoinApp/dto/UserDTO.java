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
    private String email;
    private String password;
    private boolean isEnabled;
    private LocalDate registrationDate;
    private String countryOfResidence;
    private UserRole userRole;
    private List<CurrencyDTO> currencies = null;;





    public static UserDTO fromEntity(User user) {
        List<CurrencyDTO> currencyDtos = user.getCurrencies().stream()
                .map(CurrencyDTO::from)
                .collect(Collectors.toList());

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .isEnabled(user.isEnabled())
                .registrationDate(user.getRegistrationDate())
                .countryOfResidence(user.getCountryOfResidence())
                .userRole(user.getUserRole())
                .currencies(currencyDtos)
                .build();
    }

    public User toEntity() {
        List<Currency> currencies = this.getCurrencies().stream()
                .map(CurrencyDTO::toCurrency)
                .collect(Collectors.toList());

        return User.builder()
                .id(this.getId())
                .username(this.getUsername())
                .email(this.getEmail())
                .password(this.getPassword())
                .isEnabled(this.isEnabled())
                .registrationDate(this.getRegistrationDate())
                .countryOfResidence(this.getCountryOfResidence())
                .userRole(this.getUserRole())
                .currencies(currencies)
                .build();
    }
}
