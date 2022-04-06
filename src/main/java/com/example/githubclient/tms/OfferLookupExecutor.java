package com.example.githubclient.tms;

import com.example.githubclient.model.BinanceP2PBoardResponse;
import com.example.githubclient.p2p.BinanceP2PService;
import com.example.githubclient.telegram.TelegramClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Collectors;

import static com.example.githubclient.model.AssetBinanceP2P.USDT;
import static com.example.githubclient.strategy.BinanceP2PStrategy.getSellStrategyResult;


@Component
public class OfferLookupExecutor {

    @Autowired
    private BinanceP2PService p2p;
    @Autowired
    private TelegramClient client;


    @Scheduled(cron = "0 0 5 31 2 ?")
    public void print() throws IOException {
        BinanceP2PBoardResponse buy = p2p.getP2PBuyOffers("1000", USDT);
        BinanceP2PBoardResponse sell = p2p.getP2PSellOffers("1000", USDT);
        client.sendMessage("BUY\n" + buildMessage(buy, "20000"));

        client.sendMessage("SELL\n" + getSellStrategyResult(sell, buy, "20000"));
    }

    public static String buildMessage(BinanceP2PBoardResponse response, String maxLimit) {
        double targetMaxLimit = Double.parseDouble(maxLimit);
        return response.getOffers()
            .stream()
            .filter(x -> Double.parseDouble(x.getAdv().dynamicMaxSingleTransAmount) > targetMaxLimit)
            .map(BinanceP2PBoardResponse.P2POfferInfo::toString)
            .limit(10)
            .collect(Collectors.joining("\n"));
    }
}
