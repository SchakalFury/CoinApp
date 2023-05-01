package com.example.CoinApp.services;


import com.example.CoinApp.models.Currency;
import com.example.CoinApp.models.CurrencyPrice;
import com.example.CoinApp.repositories.CurrencyPriceRepository;
import com.example.CoinApp.repositories.CurrencyRepository;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
@Service
@Component
@PropertySource("classpath:application.properties")
public class CurrencyUpdater {

    private final CurrencyPriceRepository currencyPriceRepository;
    private final CurrencyRepository currencyRepository;
    private final URL http;

    @Autowired
    public CurrencyUpdater(CurrencyRepository currencyRepository, CurrencyPriceRepository currencyPriceRepository, @Value("${currency.url}") String http) throws MalformedURLException {
        this.currencyRepository = currencyRepository;
        this.currencyPriceRepository = currencyPriceRepository;
        this.http = new URL(http);
    }

    @Scheduled(cron = "0 0 * * * *")
    public void updateCurrencies() {
        try {
            HttpURLConnection connection = (HttpURLConnection) http.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((connection.getInputStream())));

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(bufferedReader, JsonObject.class);

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

            connection.disconnect();

        } catch (Exception e) {
            log.error("An error occurred while updating currencies: ", e);
        }
    }

    @Scheduled(cron = "0 0 23 * * *")
    public void addCurrencyPrices() {
        try {
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
        } catch (Exception e) {
            log.error("An error occurred while adding currency prices: ", e);
        }
    }

    @Scheduled(cron = "0 0 0 1 1 ?")
    public void deleteOldCurrencyPrices() {
        try {
            LocalDate oneYearAgo = LocalDate.now().minusYears(1).plusDays(1); // год и один день назад
            List<CurrencyPrice> oldPrices = currencyPriceRepository.findByDateWriteBefore(oneYearAgo);
            currencyPriceRepository.deleteAll(oldPrices);
        } catch (Exception e) {
            log.error("An error occurred while deleting old currency prices: ", e);
        }
    }
}