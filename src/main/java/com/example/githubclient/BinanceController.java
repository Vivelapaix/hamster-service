package com.example.githubclient;

import com.example.githubclient.model.CryptoSymbol;
import com.example.githubclient.model.ExchangeInfo;
import org.eclipse.egit.github.core.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/binance")
public class BinanceController {
    private final BinanceService binanceService;

    public BinanceController(BinanceService binanceService) {
        this.binanceService = binanceService;
    }

    @GetMapping("/exchangePairs")
    public List<CryptoSymbol> getRepos() throws IOException {
        return binanceService.getAllExchangePairsInfo().subList(0, 20);
    }
}
