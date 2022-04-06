package com.example.githubclient.p2p;

import com.example.githubclient.model.AssetBinanceP2P;
import com.example.githubclient.model.BinanceP2PBoardRequest;
import com.example.githubclient.model.BinanceP2PBoardResponse;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

@Service
public class BinanceP2PClient {

    private static final String BINANCE_BASE_URL = "https://p2p.binance.com/bapi/c2c/v2/friendly/c2c/adv/search/";
    private final BinanceP2PApiInterface api;

    public BinanceP2PClient() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BINANCE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        api = retrofit.create(BinanceP2PApiInterface.class);
    }

    public BinanceP2PBoardResponse getP2PBuyOffers(String transAmount, AssetBinanceP2P asset) throws IOException {
        BinanceP2PBoardRequest request = new BinanceP2PBoardRequest();
        request.setPage(1);
        request.setRows(20);
        request.setPayTypes(List.of("Tinkoff"));
        request.setAsset(asset.getValue());
        request.setTradeType("BUY");
        request.setFiat("RUB");
        request.setTransAmount(transAmount);

        Call<BinanceP2PBoardResponse> retrofitCall = api.getP2PBuyOffers(request);

        Response<BinanceP2PBoardResponse> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    public BinanceP2PBoardResponse getP2PSellOffers(String transAmount, AssetBinanceP2P asset) throws IOException {
        BinanceP2PBoardRequest request = new BinanceP2PBoardRequest();
        request.setPage(1);
        request.setRows(20);
        request.setPayTypes(List.of("Tinkoff"));
        request.setAsset(asset.getValue());
        request.setTradeType("SELL");
        request.setFiat("RUB");
        request.setTransAmount(transAmount);

        Call<BinanceP2PBoardResponse> retrofitCall = api.getP2PSellOffers(request);

        Response<BinanceP2PBoardResponse> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }
}