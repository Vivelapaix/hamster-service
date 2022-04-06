package com.example.githubclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import static com.example.githubclient.utils.DrawUtils.NICKNAME_TO_EMOJI;

public class BinanceP2PBoardResponse implements Serializable {

    @SerializedName("data")
    @JsonProperty("data")
    private List<P2POfferInfo> offers;

    public List<P2POfferInfo> getOffers() {
        return offers;
    }

    public void setOffers(List<P2POfferInfo> offers) {
        this.offers = offers;
    }

    @Override
    public String toString() {
        return "BinanceP2PBoardResponse{" +
            "offers=" + offers +
            '}';
    }

    public static class P2POfferInfo {
        @SerializedName("adv")
        @JsonProperty("adv")
        private Adv adv;

        @SerializedName("advertiser")
        @JsonProperty("advertiser")
        private Advertiser advertiser;


        public Adv getAdv() {
            return adv;
        }

        public void setAdv(Adv adv) {
            this.adv = adv;
        }

        public Advertiser getAdvertiser() {
            return advertiser;
        }

        public void setAdvertiser(Advertiser advertiser) {
            this.advertiser = advertiser;
        }

        @Override
        public String toString() {
            return
                String.format("%s %s \n", NICKNAME_TO_EMOJI.getOrDefault(advertiser.nickName, "\uD83D\uDE01 "), advertiser.nickName) +
                String.format("Цена за %s: %s \n", adv.asset, adv.price) +
                String.format("Limit: от %s до %s \n", adv.minSingleTransAmount, adv.dynamicMaxSingleTransAmount) ;
        }

        public String toStringSellOfferInfo(double priceFirst) {
            return
                String.format("%s %s \n", NICKNAME_TO_EMOJI.getOrDefault(advertiser.nickName, "\uD83D\uDE01 "), advertiser.nickName) +
                String.format("Цена за %s: %s \n", adv.asset, adv.price) +
                String.format("Limit: от %s до %s \n", adv.minSingleTransAmount, adv.dynamicMaxSingleTransAmount) +
                String.format("Profit: %.3f%% \n", (priceFirst / Double.parseDouble(adv.price) - 1) * 100);
        }

        public static class Adv {
            public String price;
            public String dynamicMaxSingleTransAmount;
            public String dynamicMaxSingleTransQuantity;
            public String asset;
            public String minSingleTransAmount;

            @Override
            public String toString() {
                return "Adv{" +
                    "price='" + price + '\'' +
                    ", dynamicMaxSingleTransAmount='" + dynamicMaxSingleTransAmount + '\'' +
                    ", dynamicMaxSingleTransQuantity='" + dynamicMaxSingleTransQuantity + '\'' +
                    ", asset='" + asset + '\'' +
                    ", minSingleTransAmount='" + minSingleTransAmount + '\'' +
                    '}';
            }
        }

        public static class Advertiser {
            public String nickName;

            @Override
            public String toString() {
                return "Advertiser{" +
                    "nickName='" + nickName + '\'' +
                    '}';
            }
        }
    }
}
