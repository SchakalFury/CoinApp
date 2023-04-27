package com.example.CoinApp.utils;

import com.example.CoinApp.models.Asset;
import com.example.CoinApp.repositories.AssetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AssetUpdater {
    private static final Logger logger = LoggerFactory.getLogger(AssetUpdater.class);

    private final String url = "https://openexchangerates.org/api/latest.json?app_id=54267182114a49d7960e5dd5660605e6&base=USD";
    private final AssetRepository assetRepository;
    private final RestTemplate restTemplate;

    public AssetUpdater(AssetRepository assetRepository, RestTemplate restTemplate) {
        this.assetRepository = assetRepository;
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedDelay = 10000) // Вызываем метод раз в 10 секунд
    public void updateAssetPrices() {
        // Получаем список всех активов из базы данных
        List<Asset> assets = assetRepository.findAll();

        // Формируем список символов валют для запроса к API
        Set<String> symbols = assets.stream()
                .map(Asset::getSymbol)
                .collect(Collectors.toSet());

        // Получаем курсы валют с помощью API

        ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<Map<String, Object>>() {};
        Map<String, Object> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType).getBody();
        Map<String, Double> rates = (Map<String, Double>) response.get("rates");

        // Фильтруем курсы валют, оставляя только нужные
        Map<String, Double> filteredRates = rates.entrySet().stream()
                .filter(e -> symbols.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Обновляем цены активов
        List<Asset> updatedAssets = assets.stream()
                .map(asset -> {
                    Object value = filteredRates.get(asset.getSymbol());
                    if (value instanceof Double) {
                        BigDecimal price = BigDecimal.valueOf((Double) value);
                        asset.setAssetPrices(price);
                    } else if (value instanceof Integer) {
                        BigDecimal price = BigDecimal.valueOf((Integer) value);
                        asset.setAssetPrices(price);
                    } else {
                        logger.warn("Invalid asset price format for symbol " + asset.getSymbol() + ", ---> " + LocalDateTime.now());
                    }

                    return asset;
                })
                .collect(Collectors.toList());

        // Сохраняем обновленные активы за один раз
        assetRepository.saveAll(updatedAssets);
    }
}