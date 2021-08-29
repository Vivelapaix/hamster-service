package com.example.githubclient;

import com.example.githubclient.model.AvgPrice;
import com.example.githubclient.model.CryptoSymbol;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BinanceService {
    private final BinanceClient client;

    public BinanceService(BinanceClient client) {
        this.client = client;
    }

    public List<CryptoSymbol> getAllExchangePairsInfo() throws IOException {
        return client.getExchangeAllPairsInfo().getSymbols();
    }

    public CryptoSymbol getExchangePairInfo(String pair) throws IOException {
        return client.getExchangePairInfo(pair).getSymbols().get(0);
    }

    public AvgPrice getAvgPricePairInfo(String pair) throws IOException {
        return client.getAvgPricePairInfo(pair);
    }

    public List<List<Object>> getOneHourPriceChange(String pair) throws IOException {
        return client.getOneHourPriceChange(pair);
    }
}
