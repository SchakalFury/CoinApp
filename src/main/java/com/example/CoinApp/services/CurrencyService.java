package com.example.CoinApp.services;

import com.example.CoinApp.dto.CurrencyDTO;


import com.example.CoinApp.models.Currency;
import com.example.CoinApp.repositories.CurrencyPriceRepository;
import com.example.CoinApp.repositories.CurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Transactional
@Service
@Slf4j
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    private final CurrencyPriceRepository currencyPriceRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyService.class);

    public CurrencyService(CurrencyRepository currencyRepository,  CurrencyPriceRepository currencyPriceRepository) {
        this.currencyRepository = currencyRepository;
        this.currencyPriceRepository = currencyPriceRepository;
    }

    /**
     * Retrieves currency information by its ID.
     *
     * @param id The ID of the currency
     * @return An Optional containing the currency information as CurrencyDTO
     */
    public Optional<CurrencyDTO> findById(Long id) {
        Optional<Currency> currencyOptional = currencyRepository.findById(id);
        return currencyOptional.map(CurrencyDTO::fromEntity);
    }

    /**
     * Retrieves currencies information by their symbols.
     *
     * @param symbols The list of currency symbols
     * @return A list of currencies as CurrencyDTO
     */
    public List<CurrencyDTO> getCurrenciesBySymbols(List<String> symbols) {
        List<Currency> currencies = currencyRepository.findAllBySymbolIn(symbols);
        return CurrencyDTO.fromEntities(currencies);
    }

    /**
     * Deletes a currency by its symbol.
     *
     * @param symbol The symbol of the currency to be deleted
     */
    public void deleteCurrencyBySymbol(String symbol) {
        LOGGER.info("Removing currency with symbol {}", symbol);
        currencyRepository.deleteBySymbol(symbol);
    }

    /**
     * Retrieves all currency symbols.
     *
     * @return A list of currency symbols
     */
    public List<String> getAllCurrencySymbols() {
        List<Currency> currencies = currencyRepository.findAll();
        return currencies.stream()
                .map(Currency::getSymbol)
                .collect(Collectors.toList());
    }
}



