package com.example.githubclient.model;

public enum KlineTimeInterval {

    FIVE_MINUTES(5, "5min"),
    TEN_MINUTES(10, "10min"),
    FIFTEEN_MINUTES(15, "15min"),
    THIRTY_MINUTES(30, "30min"),
    SIXTY_MINUTES(60, "1h")

    ;

    public final int value;
    public final String description;

    KlineTimeInterval(int value, String description) {
        this.value = value;
        this.description = description;
    }
}
