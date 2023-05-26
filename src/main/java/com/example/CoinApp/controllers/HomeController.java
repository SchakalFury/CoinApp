package com.example.CoinApp.controllers;

import com.example.CoinApp.services.CurrencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final CurrencyService currencyService;

    public HomeController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<String> currencySymbols = currencyService.getAllCurrencySymbols();
        model.addAttribute("currencies", currencySymbols);
        return "index";
    }

}