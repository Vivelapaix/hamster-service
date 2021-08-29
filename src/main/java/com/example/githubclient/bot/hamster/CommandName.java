package com.example.githubclient.bot.hamster;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public enum CommandName {

    GET_ALL_EXCHANGE_PAIRS("get_all_exchange_pairs"),

    GET_EXCHANGE_PAIR_AVG_PRICE_INFO("get_exchange_pair_avg_price_info"),

    ;

    private static final Map<String, CommandName> commandByName;

    static {
        commandByName = Arrays.stream(CommandName.values())
            .collect(toMap(command -> command.name, identity()));
    }

    public final String name;

    CommandName(String value) {
        this.name = value;
    }

    public static Optional<CommandName> byName(String name) {
        return Optional.ofNullable(commandByName.get(name));
    }
}

