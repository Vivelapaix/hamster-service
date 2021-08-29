package com.example.githubclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CryptoSymbol implements Serializable {
    @SerializedName("symbol")
    @JsonProperty("symbol")
    private String symbol;
    @SerializedName("status")
    @JsonProperty("status")
    private String status;
    @SerializedName("baseAsset")
    @JsonProperty("baseAsset")
    private String baseAsset;
    @SerializedName("quoteAsset")
    @JsonProperty("quoteAsset")
    private String quoteAsset;

    public CryptoSymbol() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBaseAsset() {
        return baseAsset;
    }

    public void setBaseAsset(String baseAsset) {
        this.baseAsset = baseAsset;
    }

    public String getQuoteAsset() {
        return quoteAsset;
    }

    public void setQuoteAsset(String quoteAsset) {
        this.quoteAsset = quoteAsset;
    }

    @Override
    public String toString() {
        return "{" +
            "symbol='" + symbol + '\'' +
            ", status='" + status + '\'' +
            ", baseAsset='" + baseAsset + '\'' +
            ", quoteAsset='" + quoteAsset + '\'' +
            '}';
    }
}
