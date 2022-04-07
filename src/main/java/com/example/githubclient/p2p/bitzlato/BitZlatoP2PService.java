package com.example.githubclient.p2p.bitzlato;


import com.example.githubclient.model.AssetBinanceP2P;
import com.example.githubclient.model.bitzlato.AssetBitZlatoP2P;
import com.example.githubclient.model.bitzlato.BitZlatoP2PBoardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BitZlatoP2PService {
    private final BitZlatoP2PClient client;

    @Autowired
    public BitZlatoP2PService(BitZlatoP2PClient client) {
        this.client = client;
    }

    public BitZlatoP2PBoardResponse getP2PBuyOffers(String transAmount, AssetBitZlatoP2P asset) throws IOException {
        return client.getP2PBuyOffers(transAmount, asset);
    }

    public BitZlatoP2PBoardResponse getP2PSellOffers(String transAmount, AssetBitZlatoP2P asset) throws IOException {
        return client.getP2PSellOffers(transAmount, asset);
    }
}
