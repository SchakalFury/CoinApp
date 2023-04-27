package com.example.CoinApp.dto;

import com.example.CoinApp.models.Asset;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetDto {
    private String symbol;
    private BigDecimal assetPrices;

    public static AssetDto from(Asset asset) {
        AssetDto dto = new AssetDto();
        dto.setSymbol(asset.getSymbol());
        dto.setAssetPrices(asset.getAssetPrices());
        return dto;
    }

    public static Asset to(AssetDto assetDto) {
        Asset asset = new Asset();
        asset.setSymbol(assetDto.getSymbol());
        asset.setAssetPrices(assetDto.getAssetPrices());
        return asset;
    }
}
