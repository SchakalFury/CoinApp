package com.example.CoinApp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Table(name = "asset")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Asset {
    @Id
    @Column(name = "symbol")
    private String symbol;

    @Column(name = "asset_prices")
    private BigDecimal assetPrices;

    @ManyToMany(mappedBy = "assets")
    private Set<User> users = new HashSet<>();

    public Asset(String symbol, BigDecimal assetPrices) {
        this.symbol = symbol;
        this.assetPrices = assetPrices;
    }


}