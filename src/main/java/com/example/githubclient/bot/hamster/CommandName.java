package com.example.githubclient.bot.hamster;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public enum CommandName {

    GET_ALL_EXCHANGE_PAIRS("get_all_exchange_pairs"),

    GET_EXCHANGE_PAIR_AVG_PRICE_INFO("get_exchange_pair_avg_price_info"),

    GET_ALL_ORDERS("get_all_orders"),

    GET_MY_TRADES("get_my_trades"),

    GET_ACCOUNT("get_account"),

    P2P_BINANCE("p2p_binance@hamster_signal_bot"),

    MARKUP("markup"),

    HIDE_MARKUP("hide_markup"),

    TEMPLATE("template@hamster_signal_bot")
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

