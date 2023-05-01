package com.example.CoinApp.dto;

import com.example.CoinApp.models.Currency;
import com.example.CoinApp.models.CurrencyPrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyPriceDTO {
    private Long id;
    private BigDecimal priceDay;
    private Date dateWrite;
    private String currencySymbol;


    public static CurrencyPriceDTO from(CurrencyPrice currencyPrice) {
        return CurrencyPriceDTO.builder()
                .id(currencyPrice.getId())
                .priceDay(currencyPrice.getPriceDay())
                .dateWrite(currencyPrice.getDateWrite())
                .currencySymbol(currencyPrice.getCurrency().getSymbol())
                .build();
    }

    public CurrencyPrice to() {
        CurrencyPrice currencyPrice = CurrencyPrice.builder()
                .id(getId())
                .priceDay(getPriceDay())
                .dateWrite(getDateWrite())
                .build();
        Currency currency = new Currency();
        currency.setSymbol(getCurrencySymbol());
        currencyPrice.setCurrency(currency);
        return currencyPrice;
    }
}
