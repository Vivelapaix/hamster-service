package com.example.githubclient.telegram;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TelegramApiInterface {
    @POST("sendMessage")
    Call<Object> sendMessage(@Query("chat_id") String chatId,
                             @Query("text") String text,
                             @Query("parse_mode") String parseMode);
}

