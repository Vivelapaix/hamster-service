package com.example.githubclient.p2p;

import com.example.githubclient.model.BinanceP2PBoardRequest;
import com.example.githubclient.model.BinanceP2PBoardResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BinanceP2PApiInterface {
    @POST(".")
    Call<BinanceP2PBoardResponse> getP2PBuyOffers(@Body BinanceP2PBoardRequest requestBody);

    @POST(".")
    Call<BinanceP2PBoardResponse> getP2PSellOffers(@Body BinanceP2PBoardRequest requestBody);
}

