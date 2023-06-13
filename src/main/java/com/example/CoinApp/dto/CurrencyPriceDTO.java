package com.example.CoinApp.dto;

import com.example.CoinApp.models.Currency;
import com.example.CoinApp.models.CurrencyPrice;
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
public class CurrencyPriceDTO {
    private Long id;
    private BigDecimal priceDay;
    private Date dateWrite;
    private CurrencyDTO currency;

    public static List<CurrencyPriceDTO> fromEntities(List<CurrencyPrice> currencyPrices) {
        return currencyPrices.stream()
                .map(CurrencyPriceDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public static CurrencyPriceDTO fromEntity(CurrencyPrice currencyPrice) {
        CurrencyPriceDTO currencyPriceDTO = new CurrencyPriceDTO();
        currencyPriceDTO.setId(currencyPrice.getId());
        currencyPriceDTO.setPriceDay(currencyPrice.getPriceDay());
        currencyPriceDTO.setDateWrite(currencyPrice.getDateWrite());
        currencyPriceDTO.setCurrency(CurrencyDTO.fromEntity(currencyPrice.getCurrency()));
        return currencyPriceDTO;
    }

    public static List<CurrencyPrice> toEntities(List<CurrencyPriceDTO> currencyPriceDTOs) {
        return currencyPriceDTOs.stream()
                .map(CurrencyPriceDTO::toEntity)
                .collect(Collectors.toList());
    }

    public static CurrencyPrice toEntity(CurrencyPriceDTO currencyPriceDTO) {
        CurrencyPrice currencyPrice = new CurrencyPrice();
        currencyPrice.setId(currencyPriceDTO.getId());
        currencyPrice.setPriceDay(currencyPriceDTO.getPriceDay());
        currencyPrice.setDateWrite(currencyPriceDTO.getDateWrite());
        currencyPrice.setCurrency(CurrencyDTO.toEntity(currencyPriceDTO.getCurrency()));
        return currencyPrice;
    }
}
