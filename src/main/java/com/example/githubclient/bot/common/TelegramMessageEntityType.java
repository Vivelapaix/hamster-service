package com.example.githubclient.bot.common;

public enum TelegramMessageEntityType {

    BOT_COMMAND("bot_command"),

    ;

    public final String value;

    TelegramMessageEntityType(String value) {
        this.value = value;
    }
}
