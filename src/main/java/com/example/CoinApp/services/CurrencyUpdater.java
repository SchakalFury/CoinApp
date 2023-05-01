package com.example.CoinApp.services;


import com.example.CoinApp.models.Currency;
import com.example.CoinApp.models.CurrencyPrice;
import com.example.CoinApp.repositories.CurrencyPriceRepository;
import com.example.CoinApp.repositories.CurrencyRepository;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Component
@PropertySource("classpath:application.properties")
public class CurrencyUpdater {


    private final CurrencyPriceRepository currencyPriceRepository;
    private final CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate;
    @Value("${currency.url}")
    private String url;

    @Autowired
    public CurrencyUpdater(CurrencyRepository currencyRepository, RestTemplate restTemplate, CurrencyPriceRepository currencyPriceRepository) {
        this.currencyRepository = currencyRepository;
        this.currencyPriceRepository = currencyPriceRepository;
        this.restTemplate = restTemplate;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void updateCurrencies() {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.getBody(), JsonObject.class);

        JsonElement ratesElement = jsonObject.get("rates");
        JsonObject rates = ratesElement.getAsJsonObject();

        List<Currency> currencies = currencyRepository.findAll();

        for (Map.Entry<String, JsonElement> entry : rates.entrySet()) {
            String symbol = entry.getKey();
            BigDecimal price = entry.getValue().getAsBigDecimal();

            Optional<Currency> optionalCurrency = currencies.stream()
                    .filter(currency -> symbol.equals(currency.getSymbol()))
                    .findFirst();

            if (optionalCurrency.isPresent()) {
                Currency currency = optionalCurrency.get();
                currency.setPrice(price);
                currencyRepository.save(currency);
            }
        }
    }

    @Scheduled(cron = "0 0 23 * * *")
    public void addCurrencyPrices() {
        List<Currency> currencies = currencyRepository.findAll();
        Date currentDate = new Date();

        for (Currency currency : currencies) {
            BigDecimal currentPrice = currency.getPrice();

            CurrencyPrice currencyPrice = CurrencyPrice.builder()
                    .currency(currency)
                    .priceDay(currentPrice)
                    .dateWrite(currentDate)
                    .build();

            currencyPriceRepository.save(currencyPrice);
        }
    }

    @Scheduled(cron = "0 0 0 1 1 ?")
    public void deleteOldCurrencyPrices() {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1).plusDays(1); // год и один день назад
        List<CurrencyPrice> oldPrices = currencyPriceRepository.findByDateWriteBefore(oneYearAgo);
        currencyPriceRepository.deleteAll(oldPrices);
    }
}