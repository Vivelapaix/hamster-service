package com.example.githubclient;

import com.example.githubclient.model.Account;
import com.example.githubclient.model.AllOrders;
import com.example.githubclient.model.AvgPrice;
import com.example.githubclient.model.ExchangeInfo;
import com.example.githubclient.model.MyTrades;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @GET("allOrders")
    Call<List<AllOrders>> getAllOrders(@Query("symbol") String pair,
                                       @Query("recvWindow") long recvWindow,
                                       @Query("timestamp") long timestamp,
                                       @Query("signature") String secretSignature,
                                       @Header("Content-Type") String contentType,
                                       @Header("X-MBX-APIKEY") String apiKey);

    @GET("myTrades")
    Call<List<MyTrades>> getMyTrades(@Query("symbol") String pair,
                                     @Query("recvWindow") long recvWindow,
                                     @Query("timestamp") long timestamp,
                                     @Query("signature") String secretSignature,
                                     @Header("Content-Type") String contentType,
                                     @Header("X-MBX-APIKEY") String apiKey);

    @GET("account")
    Call<Account> getAccount(@Query("recvWindow") long recvWindow,
                             @Query("timestamp") long timestamp,
                             @Query("signature") String secretSignature,
                             @Header("Content-Type") String contentType,
                             @Header("X-MBX-APIKEY") String apiKey);
}
