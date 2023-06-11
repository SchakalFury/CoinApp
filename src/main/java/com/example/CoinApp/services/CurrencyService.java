package com.example.CoinApp.services;

import com.example.CoinApp.dto.CurrencyDTO;

import com.example.CoinApp.models.Currency;

import com.example.CoinApp.repositories.CurrencyPriceRepository;
import com.example.CoinApp.repositories.CurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Transactional
@Service
@Slf4j
public class CurrencyService {

    @Autowired
    private final CurrencyRepository currencyRepository;
    private final CurrencyPriceRepository currencyPriceRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyService.class);
    public CurrencyService(CurrencyRepository currencyRepository, CurrencyPriceRepository currencyPriceRepository) {
        this.currencyRepository = currencyRepository;
        this.currencyPriceRepository = currencyPriceRepository;
    }

    public Optional<CurrencyDTO> findById(Long id) {
        Optional<Currency> currencyOptional = currencyRepository.findById(id);
        return currencyOptional.map(CurrencyDTO::from);
    }

    public List<CurrencyDTO> getCurrenciesBySymbols(List<String> symbols) {
        List<Currency> currencies = currencyRepository.findAllBySymbolIn(symbols);
        return currencies.stream()
                .map(CurrencyDTO::from)
                .collect(Collectors.toList());
    }


    public void deleteCurrencyBySymbol(String symbol) {
        LOGGER.info("Removing currency with symbol {}", symbol);
        currencyRepository.deleteBySymbol(symbol);
    }

    public List<String> getAllCurrencySymbols() {
        List<Currency> currencies = currencyRepository.findAll();
        return currencies.stream()
                .map(Currency::getSymbol)
                .collect(Collectors.toList());
    }



}
