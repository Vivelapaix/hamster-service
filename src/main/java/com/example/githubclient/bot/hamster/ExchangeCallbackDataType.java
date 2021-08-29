package com.example.githubclient.bot.hamster;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

public enum ExchangeCallbackDataType {

    GET_PAIR_INFO("get_pair_info"),

    GET_ALL_EXCHANGE_PAIRS("get_all_exchange_pairs"),

    ;

    private static final Map<String, ExchangeCallbackDataType> commandByName;

    static {
        commandByName = Arrays.stream(ExchangeCallbackDataType.values())
            .collect(Collectors.toMap(command -> command.name, identity()));
    }

    public final String name;

    ExchangeCallbackDataType(String name) {
        this.name = name;
    }

    public static Optional<ExchangeCallbackDataType> byName(String name) {
        return Optional.ofNullable(commandByName.get(name));
    }
}
