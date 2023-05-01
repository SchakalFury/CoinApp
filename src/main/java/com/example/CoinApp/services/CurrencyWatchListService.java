package com.example.CoinApp.services;

import com.example.CoinApp.models.Currency;
import com.example.CoinApp.models.User;
import com.example.CoinApp.repositories.CurrencyRepository;
import com.example.CoinApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CurrencyWatchListService {
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    @Autowired
    public CurrencyWatchListService(UserRepository userRepository, CurrencyRepository currencyRepository) {
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
    }

    @Transactional
    public void addToWatchList(Long userId, Long currencyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        Currency currency = currencyRepository.findById(currencyId)
                .orElseThrow(() -> new IllegalArgumentException("Currency with id " + currencyId + " not found"));

        List<Currency> watchList = user.getCurrencies();
        if (!watchList.contains(currency)) {
            watchList.add(currency);
            userRepository.save(user);
        }
    }

    @Transactional
    public void removeFromWatchList(Long userId, Long currencyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        Currency currency = currencyRepository.findById(currencyId)
                .orElseThrow(() -> new IllegalArgumentException("Currency with id " + currencyId + " not found"));

        List<Currency> watchList = user.getCurrencies();
        if (watchList.contains(currency)) {
            watchList.remove(currency);
            userRepository.save(user);
        }
    }
}
