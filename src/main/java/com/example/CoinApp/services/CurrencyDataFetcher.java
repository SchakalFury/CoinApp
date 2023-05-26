package com.example.CoinApp.services;

import com.example.CoinApp.models.Currency;
import com.example.CoinApp.repositories.CurrencyRepository;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;

import java.net.URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CurrencyDataFetcher implements CommandLineRunner {
    private final CurrencyRepository currencyRepository;
    @Value("${currency.url}")
    private String currencyUrl;

    @Autowired
    public CurrencyDataFetcher(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (currencyRepository.count() == 0) {
            fetchDataAndSaveCurrencies();
        } else {
            System.out.println("Records exist in the database. No action needed.");
        }
    }

    private void fetchDataAndSaveCurrencies() {
        try {
            URL url = new URL(currencyUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            JsonElement jsonElement = JsonParser.parseReader(br);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonObject ratesObject = jsonObject.getAsJsonObject("rates");

            List<Currency> currencies = new ArrayList<>();

            for (String symbol : ratesObject.keySet()) {
                BigDecimal price = ratesObject.get(symbol).getAsBigDecimal();

                Currency currency = Currency.builder()
                        .symbol(symbol)
                        .description("")
                        .price(price)
                        .createdAt(new Date())
                        .build();

                currencies.add(currency);
            }

            currencyRepository.saveAll(currencies);
            System.out.println("UPDATE");
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
