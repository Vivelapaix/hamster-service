package com.example.githubclient;

import com.example.githubclient.model.Account;
import com.example.githubclient.model.AllOrders;
import com.example.githubclient.model.AvgPrice;
import com.example.githubclient.model.ExchangeInfo;
import com.example.githubclient.model.MyTrades;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class BinanceClient {

    private static final String BINANCE_BASE_URL = "https://api.binance.com/api/v3/";
    private static final String ALL_ORDERS_CONTENT_TYPE = "application/x-www-form-urlencoded";
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

    /**
     https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data
     */
    public List<AllOrders> getAllOrders(String pair, String apiKey, String secretKey) throws Exception {
        long timestamp = new Date().getTime();
        long startTime = timestamp - 100L * 24 * 60 * 60 * 1000;
        String query =
            "symbol=" + pair + "&" +
            "recvWindow=" + "60000" + "&" +
            //"startTime=" + String.valueOf(startTime) + "&" +
            "timestamp=" + String.valueOf(timestamp);

        String signature = sign(secretKey, query);

        Call<List<AllOrders>> retrofitCall = api.getAllOrders(
            pair,
            60000,
            timestamp,
            signature,
            ALL_ORDERS_CONTENT_TYPE,
            apiKey
        );

        Response<List<AllOrders>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    /**
     https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data
     */
    public List<MyTrades> getMyTrades(String pair, String apiKey, String secretKey) throws Exception {
        long timestamp = new Date().getTime();
        long startTime = timestamp - 100L * 24 * 60 * 60 * 1000;
        String query =
            "symbol=" + pair + "&" +
                "recvWindow=" + "60000" + "&" +
                //"startTime=" + String.valueOf(startTime) + "&" +
                "timestamp=" + String.valueOf(timestamp);

        String signature = sign(secretKey, query);

        Call<List<MyTrades>> retrofitCall = api.getMyTrades(
            pair,
            60000,
            timestamp,
            signature,
            ALL_ORDERS_CONTENT_TYPE,
            apiKey
        );

        Response<List<MyTrades>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    /**
     https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data
     */
    public Account getAccountCoins(String apiKey, String secretKey) throws Exception {
        long timestamp = new Date().getTime();
        String query = "recvWindow=60000&timestamp=" + timestamp;

        String signature = sign(secretKey, query);

        Call<Account> retrofitCall = api.getAccount(
            60000,
            timestamp,
            signature,
            ALL_ORDERS_CONTENT_TYPE,
            apiKey
        );

        Response<Account> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    // HMAC encoding
    public static String encode(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

    public static String sign(String secret, String message) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKeySpec);
            return new String(Hex.encodeHex(sha256_HMAC.doFinal(message.getBytes())));
        } catch (Exception e) {
            throw new RuntimeException("Unable to sign message.", e);
        }
    }
}
