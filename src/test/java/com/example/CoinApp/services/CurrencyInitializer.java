package com.example.CoinApp.services;

import com.example.CoinApp.models.Currency;
import com.example.CoinApp.repositories.CurrencyRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional
@PropertySource("classpath:application.properties")
public class CurrencyInitializer {
    private final CurrencyRepository currencyRepository;
    @Value("${currency.url}")
    private String http;

    @Autowired
    public CurrencyInitializer(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @PostConstruct
    public void init() {
        List<Currency> currencies = currencyRepository.findAll();
        if (currencies.isEmpty()) {
            JSONObject json = fetchCurrencyData();
            saveCurrenciesFromJson(json);
            System.out.println("Currency data initialized successfully.");
        } else {
            System.out.println("Application started.");
        }
    }

    private JSONObject fetchCurrencyData() {
        try {
            URL url = new URL(http);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            return new JSONObject(content.toString());
        } catch (IOException | JSONException e) {
            throw new RuntimeException("Failed to fetch currency data", e);
        }
    }

    private void saveCurrenciesFromJson(JSONObject json) {
        try {
            String base = json.getString("base");
            JSONArray ratesArray = json.getJSONObject("rates").names();

            List<Currency> currencies = new ArrayList<>();
            for (int i = 0; i < ratesArray.length(); i++) {
                String symbol = ratesArray.getString(i);
                BigDecimal price = BigDecimal.valueOf(json.getJSONObject("rates").getDouble(symbol));
                Currency currency = Currency.builder()
                        .symbol(symbol)
                        .price(price)
                        .createdAt(new Date())
                        .build();
                currencies.add(currency);
            }

            currencyRepository.saveAll(currencies);
        } catch (JSONException e) {
            throw new RuntimeException("Failed to parse currency data", e);
        }
    }
}
