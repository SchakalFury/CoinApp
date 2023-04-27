package com.example.CoinApp.services;


import com.example.CoinApp.dto.AssetDto;
import com.example.CoinApp.errors.ResourceNotFoundException;
import com.example.CoinApp.models.Asset;
import com.example.CoinApp.repositories.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class AssetService {
    private final AssetRepository assetRepository;


    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public AssetDto getAssetBySymbol(String symbol) {
        Asset asset = assetRepository.findBySymbol(symbol)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with symbol " + symbol));
        return AssetDto.from(asset);
    }


    public List<AssetDto> getAllAssets() {
        List<Asset> assets = assetRepository.findAll();
        return assets.stream()
                .map(AssetDto::from)
                .collect(Collectors.toList());
    }

    public List<AssetDto> searchAssetsBySymbol(String query) {
        List<Asset> assets = new ArrayList<>();
        if (query != null && !query.isEmpty()) {
            assets = assetRepository.findBySymbolContainingIgnoreCase(query);
        }
        return assets.stream()
                .map(AssetDto::from)
                .collect(Collectors.toList());
    }
}
