package com.example.githubclient.model.bitzlato;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Datum implements Serializable {
    @SerializedName("id")
    @JsonProperty("id")
    public int id;
    @SerializedName("type")
    @JsonProperty("type")
    public String type;
    @SerializedName("cryptocurrency")
    @JsonProperty("cryptocurrency")
    public String cryptocurrency;
    @SerializedName("currency")
    @JsonProperty("currency")
    public String currency;
    @SerializedName("rate")
    @JsonProperty("rate")
    public String rate;
    @SerializedName("limitCurrency")
    @JsonProperty("limitCurrency")
    public LimitCurrency limitCurrency;
    @SerializedName("limitCryptocurrency")
    @JsonProperty("limitCryptocurrency")
    public LimitCryptocurrency limitCryptocurrency;
    @SerializedName("paymethodBz")
    @JsonProperty("paymethodBz")
    public PaymethodBz paymethodBz;
    @SerializedName("paymethodId")
    @JsonProperty("paymethodId")
    public int paymethodId;
    @SerializedName("owner")
    @JsonProperty("owner")
    public String owner;

    //todo: was as Object
    @SerializedName("ownerLastActivity")
    @JsonProperty("ownerLastActivity")
    public String ownerLastActivity;

    @SerializedName("isOwnerVerificated")
    @JsonProperty("isOwnerVerificated")
    public boolean isOwnerVerificated;
    @SerializedName("safeMode")
    @JsonProperty("safeMode")
    public boolean safeMode;
    @SerializedName("ownerTrusted")
    @JsonProperty("ownerTrusted")
    public boolean ownerTrusted;
    //todo: was as Object
    @SerializedName("ownerBalance")
    @JsonProperty("ownerBalance")
    //todo: was as Object
    public String ownerBalance;
    //todo: was as Object
    @SerializedName("available")
    @JsonProperty("available")
    public String available;
    //todo: was as Object
    @SerializedName("unactiveReason")
    @JsonProperty("unactiveReason")
    public String unactiveReason;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCryptocurrency() {
        return cryptocurrency;
    }

    public void setCryptocurrency(String cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public LimitCurrency getLimitCurrency() {
        return limitCurrency;
    }

    public void setLimitCurrency(LimitCurrency limitCurrency) {
        this.limitCurrency = limitCurrency;
    }

    public LimitCryptocurrency getLimitCryptocurrency() {
        return limitCryptocurrency;
    }

    public void setLimitCryptocurrency(LimitCryptocurrency limitCryptocurrency) {
        this.limitCryptocurrency = limitCryptocurrency;
    }

    public PaymethodBz getPaymethodBz() {
        return paymethodBz;
    }

    public void setPaymethodBz(PaymethodBz paymethodBz) {
        this.paymethodBz = paymethodBz;
    }

    public int getPaymethodId() {
        return paymethodId;
    }

    public void setPaymethodId(int paymethodId) {
        this.paymethodId = paymethodId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerLastActivity() {
        return ownerLastActivity;
    }

    public void setOwnerLastActivity(String ownerLastActivity) {
        this.ownerLastActivity = ownerLastActivity;
    }

    public boolean isOwnerVerificated() {
        return isOwnerVerificated;
    }

    public void setOwnerVerificated(boolean ownerVerificated) {
        isOwnerVerificated = ownerVerificated;
    }

    public boolean isSafeMode() {
        return safeMode;
    }

    public void setSafeMode(boolean safeMode) {
        this.safeMode = safeMode;
    }

    public boolean isOwnerTrusted() {
        return ownerTrusted;
    }

    public void setOwnerTrusted(boolean ownerTrusted) {
        this.ownerTrusted = ownerTrusted;
    }

    public String getOwnerBalance() {
        return ownerBalance;
    }

    public void setOwnerBalance(String ownerBalance) {
        this.ownerBalance = ownerBalance;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getUnactiveReason() {
        return unactiveReason;
    }

    public void setUnactiveReason(String unactiveReason) {
        this.unactiveReason = unactiveReason;
    }
}
