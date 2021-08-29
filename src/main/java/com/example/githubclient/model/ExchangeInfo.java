package com.example.githubclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExchangeInfo implements Serializable {
    @SerializedName("symbols")
    @JsonProperty("symbols")
    private List<CryptoSymbol> symbols;

    public ExchangeInfo() {
    }

    public List<CryptoSymbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<CryptoSymbol> symbols) {
        this.symbols = symbols;
    }
}