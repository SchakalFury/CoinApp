package com.example.CoinApp.repositories;

import com.example.CoinApp.models.CurrencyPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CurrencyPriceRepository extends JpaRepository<CurrencyPrice, Long> {
    List<CurrencyPrice> findAllBySymbolAndDateWriteBetweenOrderByDateWrite(String symbol, LocalDate start, LocalDate end);
    List<CurrencyPrice> findAllBySymbolAndDateWriteBetweenAndPriceDayNotNullOrderByDateWrite(String symbol, LocalDate start, LocalDate end);
    List<CurrencyPrice> findAllBySymbolAndDateWriteBetweenAndPriceDayIsNullOrderByDateWrite(String symbol, LocalDate start, LocalDate end);

    List<CurrencyPrice> findByDateWriteBefore(LocalDate oneYearAgo);
}
