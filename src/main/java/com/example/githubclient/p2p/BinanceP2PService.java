package com.example.githubclient.p2p;

import com.example.githubclient.model.AssetBinanceP2P;
import com.example.githubclient.model.BinanceP2PBoardResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BinanceP2PService {
    private final BinanceP2PClient client;


    public BinanceP2PService(BinanceP2PClient client) {
        this.client = client;
    }

    public BinanceP2PBoardResponse getP2PBuyOffers(String transAmount, AssetBinanceP2P asset) throws IOException {
        BinanceP2PBoardResponse buy = client.getP2PBuyOffers(transAmount, asset);
        return buy;
    }

    public BinanceP2PBoardResponse getP2PSellOffers(String transAmount, AssetBinanceP2P asset) throws IOException {
        BinanceP2PBoardResponse sell = client.getP2PSellOffers(transAmount, asset);
        return sell;
    }
}
