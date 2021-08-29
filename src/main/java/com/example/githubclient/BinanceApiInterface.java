package com.example.githubclient;

import com.example.githubclient.model.AvgPrice;
import com.example.githubclient.model.ExchangeInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface BinanceApiInterface {
    @GET("exchangeInfo")
    Call<ExchangeInfo> getAllExchangePairs();

    @GET("exchangeInfo")
    Call<ExchangeInfo> getExchangePairInfo(@Query("symbol") String pair);

    @GET("avgPrice")
    Call<AvgPrice> getAvgPriceInfo(@Query("symbol") String pair);

    /*
        https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data
    */
    @GET("klines")
    Call<List<List<Object>>> getOneHourPriceChange(@Query("symbol") String pair,
                                         @Query("interval") String interval,
                                         @Query("limit") int limit);
}
