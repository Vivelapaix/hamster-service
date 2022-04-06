package com.example.githubclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class AllOrders implements Serializable {
    @SerializedName("symbol")
    @JsonProperty("symbol")
    private String symbol;

    @SerializedName("orderId")
    @JsonProperty("orderId")
    private Long orderId;

    @SerializedName("price")
    @JsonProperty("price")
    private String price;

    @SerializedName("status")
    @JsonProperty("status")
    private String status;

    @SerializedName("type")
    @JsonProperty("type")
    private String type;

    @SerializedName("side")
    @JsonProperty("side")
    private String side;

    @SerializedName("time")
    @JsonProperty("time")
    private Long time;

    @SerializedName("updateTime")
    @JsonProperty("updateTime")
    private Long updateTime;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "\nAllOrders{" +
            "symbol='" + symbol + '\'' +
            ", orderId=" + orderId +
            ", price='" + price + '\'' +
            ", status='" + status + '\'' +
            ", type='" + type + '\'' +
            ", side='" + side + '\'' +
            ", time=" + new Date(time) +
            ", updateTime=" + new Date(updateTime) +
            "}\n";
    }
}

