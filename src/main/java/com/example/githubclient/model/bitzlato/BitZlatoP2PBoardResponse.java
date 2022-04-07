package com.example.githubclient.model.bitzlato;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BitZlatoP2PBoardResponse implements Serializable {
   @SerializedName("data")
   @JsonProperty("data")
   private List<Data> data;

   public List<Data> getDatum() {
      return data;
   }

   public void setDatum(List<Data> data) {
      this.data = data;
   }
}

