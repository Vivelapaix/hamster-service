package com.example.githubclient.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Service
public class TelegramClient {

    private static final String BINANCE_BASE_URL = "https://api.telegram.org/bot%s/";
    private static final String DAILY_HAMSTER_LOG_CHAT_ID = "-1001617339263";
    private final TelegramApiInterface api;

    public TelegramClient(@Value("${message.sender.bot.token}") String token) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(String.format(BINANCE_BASE_URL, token))
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        api = retrofit.create(TelegramApiInterface.class);
    }

    public void sendMessage(String text) throws IOException {

        Call<Object> retrofitCall = api.sendMessage(DAILY_HAMSTER_LOG_CHAT_ID, text, "HTML");

        Response<Object> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                ? response.errorBody().string() : "Unknown error");
        }
    }
}