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
    private List<CurrencyPriceDTO> prices;
    private List<UserDTO> users;

    public static List<CurrencyDTO> fromEntities(List<Currency> currencies) {
        return currencies.stream()
                .map(CurrencyDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public static CurrencyDTO fromEntity(Currency currency) {
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setId(currency.getId());
        currencyDTO.setSymbol(currency.getSymbol());
        currencyDTO.setDescription(currency.getDescription());
        currencyDTO.setPrice(currency.getPrice());
        currencyDTO.setCreatedAt(currency.getCreatedAt());
        currencyDTO.setPrices(CurrencyPriceDTO.fromEntities(currency.getPrices()));
        currencyDTO.setUsers(UserDTO.fromEntities(currency.getUsers()));
        return currencyDTO;
    }

    public static List<Currency> toEntities(List<CurrencyDTO> currencyDTOs) {
        return currencyDTOs.stream()
                .map(CurrencyDTO::toEntity)
                .collect(Collectors.toList());
    }

    public static Currency toEntity(CurrencyDTO currencyDTO) {
        Currency currency = new Currency();
        currency.setId(currencyDTO.getId());
        currency.setSymbol(currencyDTO.getSymbol());
        currency.setDescription(currencyDTO.getDescription());
        currency.setPrice(currencyDTO.getPrice());
        currency.setCreatedAt(currencyDTO.getCreatedAt());
        currency.setPrices(CurrencyPriceDTO.toEntities(currencyDTO.getPrices()));
        currency.setUsers(UserDTO.toEntities(currencyDTO.getUsers()));
        return currency;
    }
}

