package com.example.githubclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MyTrades {
    @SerializedName("symbol")
    @JsonProperty("symbol")
    private String symbol;

    @SerializedName("price")
    @JsonProperty("price")
    private String price;

    @SerializedName("qty")
    @JsonProperty("qty")
    private String qty;

    @SerializedName("quoteQty")
    @JsonProperty("quoteQty")
    private String quoteQty;

    @SerializedName("commission")
    @JsonProperty("commission")
    private String commission;

    @SerializedName("time")
    @JsonProperty("time")
    private Long time;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getQuoteQty() {
        return quoteQty;
    }

    public void setQuoteQty(String quoteQty) {
        this.quoteQty = quoteQty;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "\nMyTrades{" +
            "symbol='" + symbol + '\'' +
            ", price='" + price + '\'' +
            ", qty='" + qty + '\'' +
            ", quoteQty='" + quoteQty + '\'' +
            ", commission='" + commission + '\'' +
            ", time=" + new Date(time) +
            "}\n";
    }
}
