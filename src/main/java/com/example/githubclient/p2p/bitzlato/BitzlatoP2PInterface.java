package com.example.githubclient.p2p.bitzlato;

import com.example.githubclient.model.bitzlato.BitZlatoP2PBoardRequest;
import com.example.githubclient.model.bitzlato.BitZlatoP2PBoardResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BitzlatoP2PInterface {
    @POST(".")
    Call<BitZlatoP2PBoardResponse> getP2PBuyOffers(@Body BitZlatoP2PBoardRequest requestBody);

    @POST(".")
    Call<BitZlatoP2PBoardResponse> getP2PSellOffers(@Body BitZlatoP2PBoardRequest requestBody);
}
