package com.example.githubclient.p2p.bitzlato;


import com.example.githubclient.model.AssetBinanceP2P;
import com.example.githubclient.model.bitzlato.AssetBitZlatoP2P;
import com.example.githubclient.model.bitzlato.BitZlatoP2PBoardRequest;
import com.example.githubclient.model.bitzlato.BitZlatoP2PBoardResponse;
import com.example.githubclient.model.bitzlato.PayMethodType;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Service
public class BitZlatoP2PClient {
    public static final String BIT_ZLATA_BASE_URL = "https://bitzlato.com/api2/p2p/public/exchange/dsa/";
    private final BitzlatoP2PInterface api;

    public BitZlatoP2PClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BIT_ZLATA_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(BitzlatoP2PInterface.class);
    }

    public BitZlatoP2PBoardResponse getP2PBuyOffers(String transAmount, AssetBitZlatoP2P asset) throws IOException {
        BitZlatoP2PBoardRequest request = new BitZlatoP2PBoardRequest();
            request.setCryptocurrency(asset.getValue());
            request.setLimit(20);
            request.setPaymethod(PayMethodType.TINKOFF.getCode());
            request.setCurrency("RUB");
            request.setOwnerActive(true);
            request.setPaymethod("purchase");
            request.setAmount(Integer.parseInt(transAmount));
            request.setAmountType("currency");

        Call<BitZlatoP2PBoardResponse> retrofitCall = api.getP2PBuyOffers(request);
        Response<BitZlatoP2PBoardResponse> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }


    public BitZlatoP2PBoardResponse getP2PSellOffers(String transAmount, AssetBitZlatoP2P asset) throws IOException {
        BitZlatoP2PBoardRequest request = new BitZlatoP2PBoardRequest();
        request.setCryptocurrency(asset.getValue());
        request.setLimit(20);
        request.setPaymethod(PayMethodType.TINKOFF.getCode());
        request.setCurrency("RUB");
        request.setOwnerActive(true);
        request.setPaymethod("selling");
        request.setAmount(Integer.parseInt(transAmount));
        request.setAmountType("currency");

        Call<BitZlatoP2PBoardResponse> retrofitCall = api.getP2PSellOffers(request);
        Response<BitZlatoP2PBoardResponse> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

}
