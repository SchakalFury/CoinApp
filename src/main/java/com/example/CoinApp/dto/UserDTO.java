package com.example.CoinApp.dto;

import com.example.CoinApp.models.Currency;
import com.example.CoinApp.models.User;
import com.example.CoinApp.models.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private boolean isEnabled;
    private LocalDate registrationDate;
    private String countryOfResidence;
    private UserRole userRole;
    private List<CurrencyDTO> currencies;


    public static UserDTO fromEntity(User user) {
        List<CurrencyDTO> currencyDtos = user.getCurrencies().stream()
                .map(CurrencyDTO::from)
                .collect(Collectors.toList());

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
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
                .isEnabled(this.isEnabled())
                .registrationDate(this.getRegistrationDate())
                .countryOfResidence(this.getCountryOfResidence())
                .userRole(this.getUserRole())
                .currencies(currencies)
                .build();
    }
}
