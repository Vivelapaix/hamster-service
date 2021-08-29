package com.example.githubclient.utils;

import com.example.githubclient.model.AvgPrice;
import com.example.githubclient.model.CryptoSymbol;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Map;

public class TelegramUtils {

    public static InlineKeyboardButton inlineKeyboardButton(String label, String data) {
        InlineKeyboardButton button = new InlineKeyboardButton(label);
        button.setCallbackData(data);
        return button;
    }

    public static String buildExchangePairText(CryptoSymbol pair) {
        if (pair == null) {
            return "No info about pair";
        }
        return pair.toString();
    }

    public static String buildAvgPricePairInfoText(String pair, AvgPrice price) {
        if (price == null) {
            return "No info about pair price: " + pair;
        }
        return pair + "\n\nAverage price: " + price.getPrice();
    }

    private TelegramUtils() {

    }
}

