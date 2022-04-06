package com.example.githubclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Account implements Serializable {
    @SerializedName("accountType")
    @JsonProperty("accountType")
    private String accountType;

    @SerializedName("balances")
    @JsonProperty("balances")
    private List<Balance> balances;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    @Override
    public String toString() {
        return "Account{" +
            "accountType='" + accountType + '\'' +
            ", balances=" + balances +
            '}';
    }

    public static class Balance {
        @SerializedName("asset")
        @JsonProperty("asset")
        private String asset;

        @SerializedName("free")
        @JsonProperty("free")
        private String free;

        @SerializedName("locked")
        @JsonProperty("locked")
        private String locked;

        public String getAsset() {
            return asset;
        }

        public void setAsset(String asset) {
            this.asset = asset;
        }

        public String getFree() {
            return free;
        }

        public void setFree(String free) {
            this.free = free;
        }

        public String getLocked() {
            return locked;
        }

        public void setLocked(String locked) {
            this.locked = locked;
        }

        public boolean isPairInMyAccount() {
            return Double.valueOf(free) + Double.valueOf(locked) > 1.0;
        }

        @Override
        public String toString() {
            return "\nBalance{" +
                "asset='" + asset + '\'' +
                ", free='" + free + '\'' +
                ", locked='" + locked + '\'' +
                "}\n";
        }
    }
}
