package com.example.CoinApp.controllers;


import com.example.CoinApp.dto.AssetDto;
import com.example.CoinApp.services.AssetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AssetController {

    private final AssetService assetService;

    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("/assets")
    public String getAllAssets(Model model) {
        List<AssetDto> assets = assetService.getAllAssets();
        model.addAttribute("assets", assets);
        return "assets";
    }

    @GetMapping("/assets/search")
    public String searchAssetsBySymbol(@RequestParam String query, Model model) {
        List<AssetDto> assets = assetService.searchAssetsBySymbol(query);
        model.addAttribute("assets", assets);
        return "assets";
    }

    @GetMapping("/assets/{symbol}")
    public String getAssetBySymbol(@PathVariable String symbol, Model model) {
        AssetDto asset = assetService.getAssetBySymbol(symbol);
        model.addAttribute("asset", asset);
        return "asset";
    }
}
