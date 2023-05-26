package com.example.CoinApp.services;

import com.example.CoinApp.models.Currency;
import com.example.CoinApp.models.CurrencyPrice;
import com.example.CoinApp.repositories.CurrencyPriceRepository;
import com.example.CoinApp.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class CurrencyPriceScheduler {
    private final CurrencyRepository currencyRepository;
    private final CurrencyPriceRepository currencyPriceRepository;

    @Autowired
    public CurrencyPriceScheduler(CurrencyRepository currencyRepository, CurrencyPriceRepository currencyPriceRepository) {
        this.currencyRepository = currencyRepository;
        this.currencyPriceRepository = currencyPriceRepository;
    }

    @Scheduled(cron = "0 0 12 * * ?", zone = "GMT")
    public void saveCurrencyPrices() {
        List<Currency> currencies = currencyRepository.findAll();
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        for (Currency currency : currencies) {
            String symbol = currency.getSymbol();
            BigDecimal priceDay = currency.getPrice();
                    Date dateWrite = currentDate;

                    CurrencyPrice currencyPrice = CurrencyPrice.builder()
                    .priceDay(priceDay)
                    .dateWrite(dateWrite)
                    .currency(currency)
                    .build();

            currencyPriceRepository.save(currencyPrice);
        }

        System.out.println("Currency prices saved for " + currencies.size() + " currencies.");
    }
}
