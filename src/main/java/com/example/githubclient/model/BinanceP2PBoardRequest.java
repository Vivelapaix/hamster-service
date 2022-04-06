package com.example.githubclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BinanceP2PBoardRequest implements Serializable {
    @SerializedName("page")
    @JsonProperty("page")
    private int page;

    @SerializedName("rows")
    @JsonProperty("rows")
    private int rows;

    @SerializedName("payTypes")
    @JsonProperty("payTypes")
    private List<String> payTypes;

    @SerializedName("publisherType")
    @JsonProperty("publisherType")
    private String publisherType;

    @SerializedName("asset")
    @JsonProperty("asset")
    private String asset;

    @SerializedName("tradeType")
    @JsonProperty("tradeType")
    private String tradeType;

    @SerializedName("fiat")
    @JsonProperty("fiat")
    private String fiat;

    @SerializedName("transAmount")
    @JsonProperty("transAmount")
    private String transAmount;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<String> getPayTypes() {
        return payTypes;
    }

    public void setPayTypes(List<String> payTypes) {
        this.payTypes = payTypes;
    }

    public String getPublisherType() {
        return publisherType;
    }

    public void setPublisherType(String publisherType) {
        this.publisherType = publisherType;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getFiat() {
        return fiat;
    }

    public void setFiat(String fiat) {
        this.fiat = fiat;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }
}