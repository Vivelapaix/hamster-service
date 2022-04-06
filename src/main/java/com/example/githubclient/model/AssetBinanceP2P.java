package com.example.githubclient.model;

public enum AssetBinanceP2P {
    USDT("USDT"),
    BTC("BTC");

    public final String value;

    AssetBinanceP2P(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
