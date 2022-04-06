package com.example.githubclient.bot.hamster;

import com.example.githubclient.BinanceService;
import com.example.githubclient.bot.SimpleLongPollingBot;
import com.example.githubclient.bot.common.BotCommand;
import com.example.githubclient.bot.common.TelegramUpdateMessage;
import com.example.githubclient.config.HamsterBotSettings;
import com.example.githubclient.model.Account;
import com.example.githubclient.model.AllOrders;
import com.example.githubclient.model.AssetBinanceP2P;
import com.example.githubclient.model.AvgPrice;
import com.example.githubclient.model.BinanceP2PBoardResponse;
import com.example.githubclient.model.CryptoSymbol;
import com.example.githubclient.model.MyTrades;
import com.example.githubclient.p2p.BinanceP2PService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static com.example.githubclient.bot.hamster.ExchangeCallbackDataType.GET_ALL_EXCHANGE_PAIRS;
import static com.example.githubclient.bot.hamster.ExchangeCallbackDataType.GET_PAIR_INFO;
import static com.example.githubclient.model.AssetBinanceP2P.BTC;
import static com.example.githubclient.model.AssetBinanceP2P.USDT;
import static com.example.githubclient.strategy.BinanceP2PStrategy.getSellStrategyResult;
import static com.example.githubclient.tms.OfferLookupExecutor.buildMessage;
import static com.example.githubclient.utils.TelegramUtils.buildAvgPricePairInfoText;
import static com.example.githubclient.utils.TelegramUtils.inlineKeyboardButton;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Component
public class HamsterBot extends SimpleLongPollingBot<HamsterBot, HamsterBotSettings> {

    private final BinanceService binanceService;
    private BinanceP2PService p2p;

    @Autowired
    public HamsterBot(HamsterBotSettings settings, BinanceService binanceService, BinanceP2PService p2p) {
        super(settings, HamsterBot.class);
        this.binanceService = binanceService;
        this.p2p = p2p;
    }

    @Override
    public String getBotUsername() {
        return settings.getName();
    }

    @Override
    public String getBotToken() {
        return settings.getToken();
    }

    @Override
    protected void onMessageReceived(TelegramUpdateMessage message) {
        if (message.isChannelCommand()) {
            message.asChannelBotMessage(CommandName::byName).ifPresentOrElse(
                command -> {
                    try {
                        executeCommand(
                            message.raw().getMessage().getFrom().getId().toString(),
                            message.raw().getMessage().getFrom().getUserName(),
                            message.raw().getMessage().getChatId(),
                            command
                        );
                    } catch (IOException e) {
                        sendMessage(new SendMessage(message.raw().getMessage().getChatId(), "Проверьте название пары или параметры команды"));
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                () -> log.warn("Failed to parse command: msg={}", message)
            );
        } else if (message.isCallbackQuery()) {
            Long chatId = message.raw().getCallbackQuery().getMessage().getChatId();
            Integer userId = message.raw().getCallbackQuery().getFrom().getId();
            List<String> data = Arrays.stream(message.raw().getCallbackQuery().getData().split(" ")).collect(toList());
            ExchangeCallbackDataType.byName(data.get(0))
                .ifPresentOrElse(
                    callback -> {
                        try {
                            executeProjectCallback(chatId, userId, callback, data.subList(1, data.size()), message.raw().getCallbackQuery().getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    },
                    () -> executeUnknownCallback(chatId)
                );
        } else if (message.isChannelMessage()) {
            try {
                executeTextCommand(message.raw().getMessage().getChatId(), message.raw().getMessage().getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void executeCommand(String userId, String login, Long chatId, BotCommand<CommandName> command) throws Exception {
        switch (command.name()) {
            case P2P_BINANCE:
                sendP2POffers(chatId);
                break;
            case GET_ALL_EXCHANGE_PAIRS:
                sendAllExchangePairsKeyboard(chatId);
                break;
            case GET_EXCHANGE_PAIR_AVG_PRICE_INFO:
                sendExchangePairAvgPriceInfo(chatId, command.arg(0).orElse("BTCUSDT").toUpperCase());
                break;
            case GET_ALL_ORDERS:
                sendAllOrders(chatId, command.arg(0).orElse("BTCUSDT").toUpperCase());
                break;
            case GET_MY_TRADES:
                sendMyTrades(chatId, command.arg(0).orElse("BTCUSDT").toUpperCase());
                break;
            case GET_ACCOUNT:
                sendAccountCoins(chatId);
                break;
            case MARKUP:
                sendMarkupBoard(chatId, command.args());
                break;
            case HIDE_MARKUP:
                sendHideMarkupBoard(chatId);
                break;
            case TEMPLATE:
                sendP2PTemplate(chatId);
                break;
            default:
                log.error("Unsupported command: command={}", command);
        }
    }

    private void executeTextCommand(Long chatId, String text) throws Exception {
        String[] s = text.split(" ");
        switch (s[0]) {
            case "BUY":
                sendBuyP2POffers(chatId, s);
                break;
            case "SELL":
                sendSellP2POffers(chatId, s);
                break;
            case "CANCEL":
                sendHideMarkupBoard(chatId);
                break;
            default:
                log.error("Unsupported command: command={}", text);
        }
    }

    private void executeProjectCallback(Long chatId, Integer userId, ExchangeCallbackDataType callbackDataType, List<String> params, Message original) throws IOException {
        switch (callbackDataType) {
            case GET_PAIR_INFO:
                sendExchangePairInfo(chatId, params.get(0).toUpperCase());
                break;
            case GET_ALL_EXCHANGE_PAIRS:
                sendAllExchangePairsKeyboard(chatId);
                break;
            default:
                executeUnsupportedCommand(chatId);
        }
    }

    private void sendExchangePairInfo(Long chatId, String pair) throws IOException {
        AvgPrice exchangePairInfo = binanceService.getAvgPricePairInfo(pair);
        SendMessage message = new SendMessage(chatId,
            buildAvgPricePairInfoText(pair, exchangePairInfo) + "\n\n" + buildPricePercentChange(pair)
        );
        ImmutableList<InlineKeyboardButton> actionButtons = ImmutableList.of(
            actionButton("Назад", GET_ALL_EXCHANGE_PAIRS.name)
        );
        message.setReplyMarkup(returnToAllPairs(actionButtons));
        sendMessage(message);
    }

    private void sendExchangePairAvgPriceInfo(Long chatId, String pair) throws IOException {
        AvgPrice exchangePairInfo = binanceService.getAvgPricePairInfo(pair);
        SendMessage message = new SendMessage(chatId,
            buildAvgPricePairInfoText(pair, exchangePairInfo) + "\n\n" + buildPricePercentChange(pair)
        );
        sendMessage(message);
    }

    private void sendAllOrders(Long chatId, String pair) throws Exception {
        List<AllOrders> allOrders = binanceService.getAllOrders(pair);
        SendMessage message = new SendMessage(chatId, allOrders.toString());
        sendMessage(message);
    }

    private void sendMyTrades(Long chatId, String pair) throws Exception {
        List<MyTrades> allOrders = binanceService.getMyTrades(pair);
        SendMessage message = new SendMessage(chatId, allOrders.toString());
        sendMessage(message);
    }

    private void sendAccountCoins(Long chatId) throws Exception {
        List<Account.Balance> accountCoins = binanceService.getAccountCoins();
        for (Account.Balance coin : accountCoins) {
            String pair = getUsdtPair(coin.getAsset());
            sendMessage(chatId, pair);
            /*try {
                List<AllOrders> allOrders = binanceService.getAllOrders(pair);
                sendMessage(chatId, allOrders.toString());
            } catch (Exception e) {
                sendMessage(chatId, e.getMessage());
            }*/
        }
    }

    private void sendMarkupBoard(Long chatId, List<String> args) throws Exception {

        SendMessage message = new SendMessage() // Create a message object object
            .setChatId(chatId)
            .setText("Here is your keyboard");
        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        changeLastElementValue(args, USDT);
        row.add("BUY " + String.join(" ", args));
        row.add("SELL "+ String.join(" ", args));
        //row.add("Row 1 Button 3");
        // Add the first row to the keyboard
        keyboard.add(row);
        // Create another keyboard row
        row = new KeyboardRow();
        // Set each button for the second line
        changeLastElementValue(args, BTC);
        row.add("BUY " + String.join(" ", args));
        row.add("SELL "+ String.join(" ", args));
        //row.add("Row 2 Button 2");
        //row.add("Row 2 Button 3");
        // Add the second row to the keyboard
        keyboard.add(row);


        row = new KeyboardRow();
        // Set each button for the second line
        row.add("CANCEL");
        //row.add("Row 2 Button 2");
        //row.add("Row 2 Button 3");
        // Add the second row to the keyboard
        keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        keyboardMarkup.setResizeKeyboard(true);
        message.setReplyMarkup(keyboardMarkup);
        sendMessage(message); // Sending our message object to user
    }

    private void changeLastElementValue(List<String> args, AssetBinanceP2P value) {
        int index = args.size() - 1;
        args.remove(index);
        args.add(value.getValue());
    }

    private void sendHideMarkupBoard(Long chatId) throws Exception {
        SendMessage msg = new SendMessage()
            .setChatId(chatId)
            .setText("Keyboard hidden");
        ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
        msg.setReplyMarkup(keyboardMarkup);
        sendMessage(msg); // Call method to send the photo
    }

    private void sendP2PTemplate(Long chatId) throws Exception {
        SendMessage msg = new SendMessage()
            .setChatId(chatId)
            .setText("#/markup Сумма 1000 МаксЛимит 20000 Монета USDT");

        sendMessage(msg); // Call method to send the photo
    }

    private String getUsdtPair(String coin) {
        return coin + "USDT";
    }

    private String buildPricePercentChange(String pair) throws IOException {
        List<List<Object>> oneHourPriceChange = binanceService.getOneHourPriceChange(pair);
        int size = oneHourPriceChange.size();
        Double lastClose = Double.valueOf(oneHourPriceChange.get(size - 1).get(4).toString());
        Double lastOpen = Double.valueOf(oneHourPriceChange.get(size - 1).get(1).toString());
        return "1minChange: " + lastClose + "\n\n" +
            IntStream.range(2, 12)
            .mapToObj(x -> x + "minChange: " + getPricePercentChange(lastClose, size - x, oneHourPriceChange))
            .collect(Collectors.joining("\n\n"));
    }

    private String getPricePercentChange(Double lastClose, int interval, List<List<Object>> oneHourPriceChange) {
        // https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data
        // https://www.reddit.com/r/binance/comments/hy3gd6/getting_one_hour_price_change_through_the_api/
        Double open = Double.valueOf(oneHourPriceChange.get(interval).get(1).toString());
        return oneHourPriceChange.get(interval).get(4) + " (" + ((lastClose - open) * 100.0 / open) + "%)";
    }

    private InlineKeyboardMarkup returnToAllPairs(ImmutableList<InlineKeyboardButton> actionButtons) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(singletonList(actionButtons));
        return markup;
    }

    private InlineKeyboardButton actionButton(String buttonName, String callBackString) {
        InlineKeyboardButton button = new InlineKeyboardButton(buttonName);
        button.setCallbackData(callBackString);
        return button;
    }

    private void sendAllExchangePairsKeyboard(Long chatId) throws IOException {
        SendMessage message = new SendMessage(chatId, "Список пар:");
        message.setReplyMarkup(chooseAllExchangePairsKeyboard());
        sendMessage(message);
    }

    private void sendP2POffers(Long chatId) throws IOException {
        BinanceP2PBoardResponse buy = p2p.getP2PBuyOffers("3000", USDT);
        BinanceP2PBoardResponse sell = p2p.getP2PSellOffers("3000", USDT);
        SendMessage message = new SendMessage(chatId, "BUY\n" + buildMessage(buy, "20000"));
        sendMessage(message);
        message = new SendMessage(chatId, "SELL\n" + getSellStrategyResult(sell, buy, "20000"));
        sendMessage(message);
    }

    private void sendBuyP2POffers(Long chatId, String[] s) throws IOException {
        BinanceP2PBoardResponse buy = p2p.getP2PBuyOffers(s[2], AssetBinanceP2P.valueOf(s[6]));
        SendMessage message = new SendMessage(chatId, "BUY\n\n" + buildMessage(buy, s[4]));
        sendMessage(message);
    }

    private void sendSellP2POffers(Long chatId, String[] s) throws IOException {
        BinanceP2PBoardResponse buy = p2p.getP2PBuyOffers(s[2], AssetBinanceP2P.valueOf(s[6]));
        BinanceP2PBoardResponse sell = p2p.getP2PSellOffers(s[2], AssetBinanceP2P.valueOf(s[6]));
        SendMessage message = new SendMessage(chatId, "SELL\n\n" + getSellStrategyResult(sell, buy, s[4]));
        sendMessage(message);
    }

    private InlineKeyboardMarkup chooseAllExchangePairsKeyboard() throws IOException {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<CryptoSymbol> pairs = binanceService.getAllExchangePairsInfo();
        Iterable<List<InlineKeyboardButton>> buttons = Iterables.partition(
            () -> pairs.stream()
                .filter(x -> x.getStatus().equals("TRADING"))
                .filter(x -> x.getSymbol().endsWith("USDT"))
                .map(pair ->
                    inlineKeyboardButton(pair.getSymbol(),
                        GET_PAIR_INFO.name + " " + pair.getSymbol()))
                .limit(50)
                .iterator(),
            1
        );
        markup.setKeyboard(StreamSupport.stream(buttons.spliterator(), false).collect(toList()));
        return markup;
    }

    private void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Failed to send Telegram message: message=" + message, e);
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        sendMessage(message);
    }

    private void deleteMessage(Long chatId, Integer messageId) {
        try {
            execute(new DeleteMessage(chatId, messageId));
        } catch (TelegramApiException e) {
            log.error("Failed to delete Telegram message: message=" + messageId, e);
        }
    }

    private void executeUnknownCallback(Long chatId) {
        sendMessage(new SendMessage(chatId, "unknown callback"));
    }

    private void executeUnsupportedCommand(Long chatId) {
        sendMessage(new SendMessage(chatId, "unsupported command"));
    }

}
