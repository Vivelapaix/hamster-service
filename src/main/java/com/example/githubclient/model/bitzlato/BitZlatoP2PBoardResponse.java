package com.example.githubclient.model.bitzlato;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BitZlatoP2PBoardResponse implements Serializable {
   @SerializedName("data")
   @JsonProperty("data")
   private List<Datum> datum;

   public List<Datum> getDatum() {
      return datum;
   }

   public void setDatum(List<Datum> datum) {
      this.datum = datum;
   }
}

