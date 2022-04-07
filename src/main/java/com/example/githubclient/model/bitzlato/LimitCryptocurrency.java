package com.example.githubclient.model.bitzlato;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LimitCryptocurrency implements Serializable {
    @SerializedName("min")
    @JsonProperty("min")
    public String min;
    @SerializedName("max")
    @JsonProperty("max")
    public String max;

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getRealMax() {
        return realMax;
    }

    public void setRealMax(String realMax) {
        this.realMax = realMax;
    }

    @SerializedName("realMax")
    @JsonProperty("realMax")
    public String realMax;
}
