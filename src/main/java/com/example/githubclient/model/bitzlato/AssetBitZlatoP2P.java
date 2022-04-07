package com.example.githubclient.model.bitzlato;

public enum AssetBitZlatoP2P {
    USDT("USDT"),
    BTC("BTC");

    public final String value;

    AssetBitZlatoP2P(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
