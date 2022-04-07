package com.example.githubclient.model.bitzlato;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PaymethodBz implements Serializable {
    @SerializedName("id")
    @JsonProperty("id")
    public int id;
    @SerializedName("name")
    @JsonProperty("name")
    public String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
