package com.example.githubclient.strategy;

import com.example.githubclient.model.BinanceP2PBoardResponse;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BinanceP2PStrategy {
    public static List<Pair<String, Double>> user = List.of(Pair.of("eduardo", 90000.00));

    public static List<String> getBuyStrategyResult() {
        return null;
    }

    public static String getSellStrategyResult(BinanceP2PBoardResponse sellOffers,
                                               BinanceP2PBoardResponse buyOffers,
                                               String maxLimit) {
        double targetMaxLimit = Double.parseDouble(maxLimit);
        double priceFirst = buyOffers.getOffers()
            .stream()
            .map(x -> Pair.of(Double.parseDouble(x.getAdv().dynamicMaxSingleTransAmount), Double.parseDouble(x.getAdv().price)))
            .filter(x -> x.getLeft() > targetMaxLimit)
            .map(Pair::getRight)
            .findFirst()
            .get();
        Optional<BinanceP2PBoardResponse.P2POfferInfo> first = sellOffers.getOffers()
            .stream()
            .filter(x -> user.get(0).getLeft().equals(x.getAdvertiser().nickName))
            .findFirst();
        String result = null;
        if (first.isPresent()) {
            String userName = first.get().getAdvertiser().nickName;
            Double userPrice = Double.parseDouble(first.get().getAdv().price);
            Double userMaxLimit = Double.parseDouble(first.get().getAdv().dynamicMaxSingleTransAmount);
            result = sellOffers.getOffers()
                .stream()
                .filter(x -> checkSellStrategy(x, userName, userPrice, userMaxLimit, targetMaxLimit))
                .map(x -> x.toStringSellOfferInfo(priceFirst))
                .collect(Collectors.joining("\n"));
        } else {
            result = sellOffers.getOffers()
                .stream()
                .filter(x -> Double.parseDouble(x.getAdv().dynamicMaxSingleTransAmount) > targetMaxLimit)
                .map(x -> x.toStringSellOfferInfo(priceFirst))
                .limit(10)
                .collect(Collectors.joining("\n"));
        }
        return result;
    }

    private static boolean checkSellStrategy(BinanceP2PBoardResponse.P2POfferInfo info,
                                             String userName,
                                             Double userPrice,
                                             Double userMaxLimit,
                                             double targetMaxLimit) {
        double price = Double.parseDouble(info.getAdv().price);
        double maxLimit = Double.parseDouble(info.getAdv().dynamicMaxSingleTransAmount);
        //return userName.equals(info.getAdvertiser().nickName) || (maxLimit > userMaxLimit / 2.0 && price > userPrice);
        return userName.equals(info.getAdvertiser().nickName) || (maxLimit > targetMaxLimit);
    }
}
