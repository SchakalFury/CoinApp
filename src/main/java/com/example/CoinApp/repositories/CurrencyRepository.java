package com.example.CoinApp.repositories;

import com.example.CoinApp.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    List<Currency> findAllBySymbolIn(List<String> symbols);

    void deleteBySymbol(String symbol);
}