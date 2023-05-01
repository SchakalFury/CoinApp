package com.example.CoinApp.services;

import com.example.CoinApp.dto.CurrencyDTO;
import com.example.CoinApp.dto.CurrencyPriceDTO;
import com.example.CoinApp.models.Currency;
import com.example.CoinApp.models.CurrencyPrice;
import com.example.CoinApp.repositories.CurrencyPriceRepository;
import com.example.CoinApp.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CurrencyService {
    @Autowired
    private final CurrencyRepository currencyRepository;
    private final CurrencyPriceRepository currencyPriceRepository;

    public CurrencyService(CurrencyRepository currencyRepository, CurrencyPriceRepository currencyPriceRepository) {
        this.currencyRepository = currencyRepository;
        this.currencyPriceRepository = currencyPriceRepository;
    }

    public List<CurrencyDTO> getCurrenciesBySymbols(List<String> symbols) {
        List<Currency> currencies = currencyRepository.findAllBySymbolIn(symbols);
        return currencies.stream()
                .map(CurrencyDTO::from)
                .collect(Collectors.toList());
    }


    public void deleteCurrencyBySymbol(String symbol) {
        currencyRepository.deleteBySymbol(symbol);
    }


    public List<CurrencyPriceDTO> findCurrencyPricesBySymbolAndDateWriteBetween(String symbol, LocalDate start, LocalDate end) {
        List<CurrencyPrice> currencyPrices = currencyPriceRepository.findAllBySymbolAndDateWriteBetweenOrderByDateWrite(symbol, start, end);
        return currencyPrices.stream()
                .map(CurrencyPriceDTO::from)
                .collect(Collectors.toList());
    }

    public List<CurrencyPriceDTO> findCurrencyPricesBySymbolAndDateWriteBetweenAndPriceDayNotNull(String symbol, LocalDate start, LocalDate end) {
        List<CurrencyPrice> currencyPrices = currencyPriceRepository.findAllBySymbolAndDateWriteBetweenAndPriceDayNotNullOrderByDateWrite(symbol, start, end);
        return currencyPrices.stream()
                .map(CurrencyPriceDTO::from)
                .collect(Collectors.toList());
    }

    public List<CurrencyPriceDTO> findCurrencyPricesBySymbolAndDateWriteBetweenAndPriceDayIsNull(String symbol, LocalDate start, LocalDate end) {
        List<CurrencyPrice> currencyPrices = currencyPriceRepository.findAllBySymbolAndDateWriteBetweenAndPriceDayIsNullOrderByDateWrite(symbol, start, end);
        return currencyPrices.stream()
                .map(CurrencyPriceDTO::from)
                .collect(Collectors.toList());
    }
}