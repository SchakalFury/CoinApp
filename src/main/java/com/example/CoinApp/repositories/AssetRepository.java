package com.example.CoinApp.repositories;

import com.example.CoinApp.models.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findBySymbolContainingIgnoreCase(String query);

    Optional<Asset> findBySymbol(String symbol);

}
