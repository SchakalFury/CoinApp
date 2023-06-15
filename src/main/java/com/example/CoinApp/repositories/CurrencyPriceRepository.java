package com.example.CoinApp.repositories;

import com.example.CoinApp.models.Currency;
import com.example.CoinApp.models.CurrencyPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface CurrencyPriceRepository extends JpaRepository<CurrencyPrice, Long> {

    List<CurrencyPrice> findByDateWriteBefore(LocalDate oneYearAgo);

    List<CurrencyPrice> findByCurrencySymbolAndDateWriteBetween(String currencySymbol, LocalDateTime startDate, LocalDateTime endDate);

    int deleteByDateWriteBefore(Date thresholdDate);
}
