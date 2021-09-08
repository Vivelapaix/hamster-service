package com.example.githubclient;

import com.example.githubclient.model.AllOrders;
import com.example.githubclient.model.AvgPrice;
import com.example.githubclient.model.CryptoSymbol;
import com.example.githubclient.model.MyTrades;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BinanceService {
    private final BinanceClient client;
    private final String apiKey;
    private final String secretKey;

    public BinanceService(BinanceClient client,
                          @Value("${binance.api.api.key}") String apiKey,
                          @Value("${binance.api.secret.key}") String secretKey) {
        this.client = client;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
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

    public List<AllOrders> getAllOrders(String pair) throws Exception {
        return client.getAllOrders(pair, apiKey, secretKey);
    }

    public List<MyTrades> getMyTrades(String pair) throws Exception {
        return client.getMyTrades(pair, apiKey, secretKey);
    }
}
