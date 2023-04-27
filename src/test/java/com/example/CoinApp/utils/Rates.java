package com.example.CoinApp.utils;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Rates {
    private String jsonResponse;

    public Rates(String jsonResponse) {
        this.jsonResponse = jsonResponse;
    }

    public Map<String, Double> getRates() throws JSONException {
        Map<String, Double> rates = new HashMap<>();
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONObject ratesObject = jsonObject.getJSONObject("rates");
        Iterator<String> keys = ratesObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Double rate = ratesObject.getDouble(key);
            rates.put(key, rate);
        }
        return rates;
    }
}
