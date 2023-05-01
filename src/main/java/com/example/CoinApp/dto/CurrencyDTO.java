package com.example.CoinApp.dto;

import com.example.CoinApp.models.Currency;
import com.example.CoinApp.models.CurrencyPrice;
import com.example.CoinApp.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyDTO {
    private Long id;
    private String symbol;
    private String description;
    private BigDecimal price;
    private Date createdAt;
    private List<UserDTO> users;
    private List<CurrencyPriceDTO> prices;


    public static CurrencyDTO from(Currency currency) {
        List<CurrencyPriceDTO> prices = currency.getPrices().stream()
                .map(CurrencyPriceDTO::from)
                .collect(Collectors.toList());
        List<UserDTO> users = currency.getUsers().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
        return CurrencyDTO.builder()
                .id(currency.getId())
                .symbol(currency.getSymbol())
                .description(currency.getDescription())
                .price(currency.getPrice())
                .createdAt(currency.getCreatedAt())
                .prices(prices)
                .users(users)
                .build();
    }

    public Currency toCurrency() {
        Currency currency = Currency.builder()
                .id(getId())
                .symbol(getSymbol())
                .description(getDescription())
                .price(getPrice())
                .createdAt(getCreatedAt())
                .build();
        List<CurrencyPrice> prices = getPrices().stream()
                .map(CurrencyPriceDTO::to)
                .collect(Collectors.toList());
        currency.setPrices(prices);
        List<User> users = getUsers().stream()
                .map(UserDTO::toEntity)
                .collect(Collectors.toList());
        currency.setUsers(users);
        return currency;
    }
}

