package com.example.githubclient.model.bitzlato;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class BitZlatoP2PBoardRequest implements Serializable {
    @SerializedName("limit")
    @JsonProperty("limit")
    private int limit;
    @SerializedName("type")
    @JsonProperty("type")
    private String type;
    @SerializedName("currency")
    @JsonProperty("currency")
    private String currency;
    @SerializedName("cryptocurrency")
    @JsonProperty("cryptocurrency")
    private String cryptocurrency;
    @SerializedName("paymethod")
    @JsonProperty("paymethod")
    private String paymethod;
    @SerializedName("isOwnerVerificated")
    @JsonProperty("isOwnerVerificated")
    private boolean isOwnerVerificated;
    @SerializedName("isOwnerTrusted")
    @JsonProperty("isOwnerTrusted")
    private boolean isOwnerTrusted;
    @SerializedName("isOwnerActive")
    @JsonProperty("isOwnerActive")
    private boolean isOwnerActive;
    @SerializedName("amount")
    @JsonProperty("amount")
    private int amount;
    @SerializedName("amountType")
    @JsonProperty("amountType")
    private String amountType;


    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getLimit() {
        return limit;
    }

    public String getType() {
        return type;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCryptocurrency() {
        return cryptocurrency;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public boolean isOwnerVerificated() {
        return isOwnerVerificated;
    }

    public boolean isOwnerTrusted() {
        return isOwnerTrusted;
    }

    public boolean isOwnerActive() {
        return isOwnerActive;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCryptocurrency(String cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public void setOwnerVerificated(boolean ownerVerificated) {
        isOwnerVerificated = ownerVerificated;
    }

    public void setOwnerTrusted(boolean ownerTrusted) {
        isOwnerTrusted = ownerTrusted;
    }

    public void setOwnerActive(boolean ownerActive) {
        isOwnerActive = ownerActive;
    }
}

