package com.example.CoinApp.utils;


import com.example.CoinApp.models.Asset;
import com.example.CoinApp.repositories.AssetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AssetChecker {
    private static final Logger logger = LoggerFactory.getLogger(AssetChecker.class);
    private final String url;
    private final RestTemplate restTemplate;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    public AssetChecker(RestTemplate restTemplate, @Value("${api.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }
    @PostConstruct
    public void init() {
        List<Asset> assets = assetRepository.findAll();

        if (assets.isEmpty()) {
            logger.info("There is no list of monitored assets in the database. Update in progress.... ---> " +  LocalDateTime.now());
            requestToServer(url);
            logger.info(" The list of monitored assets has been updated. ---> " +  LocalDateTime.now());
        } else {
            logger.info(" Updating the list of monitored assets in the database is not required. ---> " +  LocalDateTime.now());
        }
    }


    public void requestToServer(String url) {
        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {});
            Map<String, Object> responseBody = response.getBody();
            Map<String, Object> rates = (Map<String, Object>) responseBody.get("rates");
            List<Asset> newAssets = new ArrayList<>();
            for (Map.Entry<String, Object> entry : rates.entrySet()) {
                String symbol = entry.getKey();
                Object value = entry.getValue();
                if (symbol == null || symbol.isEmpty()) {
                    logger.warn("Symbol is null or empty");
                    continue;
                }
                BigDecimal assetPrices;
                if (value == null) {
                    logger.warn("Asset price is null");
                    continue;
                }
                try {
                    assetPrices = BigDecimal.valueOf(((Number) value).doubleValue());
                } catch (Exception e) {
                    logger.warn("Invalid asset price format: " + e.getMessage());
                    continue;
                }
                Asset asset = new Asset(symbol, assetPrices);
                newAssets.add(asset);
            }
            assetRepository.saveAll(newAssets);
        } catch (Exception e) {
            logger.error("Error retrieving assets from server: " + e.getMessage());
        }
    }
}