package com.example.githubclient;

import com.example.githubclient.model.AvgPrice;
import com.example.githubclient.model.ExchangeInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

@Service
public class BinanceClient {

    private static final String BINANCE_BASE_URL = "https://api.binance.com/api/v3/";
    private final BinanceApiInterface api;

    public BinanceClient() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BINANCE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        api = retrofit.create(BinanceApiInterface.class);
    }

    public ExchangeInfo getExchangeAllPairsInfo() throws IOException {
        Call<ExchangeInfo> retrofitCall = api.getAllExchangePairs();

        Response<ExchangeInfo> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    public ExchangeInfo getExchangePairInfo(String pair) throws IOException {
        Call<ExchangeInfo> retrofitCall = api.getExchangePairInfo(pair);

        Response<ExchangeInfo> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    public AvgPrice getAvgPricePairInfo(String pair) throws IOException {
        Call<AvgPrice> retrofitCall = api.getAvgPriceInfo(pair);

        Response<AvgPrice> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    public List<List<Object>> getOneHourPriceChange(String pair) throws IOException {
        Call<List<List<Object>>> retrofitCall = api.getOneHourPriceChange(pair, "1m", 20);

        Response<List<List<Object>>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }
}
