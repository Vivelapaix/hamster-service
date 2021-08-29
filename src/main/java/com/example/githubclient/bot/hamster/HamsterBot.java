package com.example.githubclient.bot.hamster;

import com.example.githubclient.BinanceService;
import com.example.githubclient.bot.SimpleLongPollingBot;
import com.example.githubclient.bot.common.BotCommand;
import com.example.githubclient.bot.common.TelegramUpdateMessage;
import com.example.githubclient.config.HamsterBotSettings;
import com.example.githubclient.model.AvgPrice;
import com.example.githubclient.model.CryptoSymbol;
import com.example.githubclient.model.KlineTimeInterval;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static com.example.githubclient.bot.hamster.ExchangeCallbackDataType.GET_ALL_EXCHANGE_PAIRS;
import static com.example.githubclient.bot.hamster.ExchangeCallbackDataType.GET_PAIR_INFO;
import static com.example.githubclient.model.KlineTimeInterval.FIFTEEN_MINUTES;
import static com.example.githubclient.model.KlineTimeInterval.FIVE_MINUTES;
import static com.example.githubclient.model.KlineTimeInterval.SIXTY_MINUTES;
import static com.example.githubclient.model.KlineTimeInterval.TEN_MINUTES;
import static com.example.githubclient.model.KlineTimeInterval.THIRTY_MINUTES;
import static com.example.githubclient.utils.TelegramUtils.buildAvgPricePairInfoText;
import static com.example.githubclient.utils.TelegramUtils.buildExchangePairText;
import static com.example.githubclient.utils.TelegramUtils.inlineKeyboardButton;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Component
public class HamsterBot extends SimpleLongPollingBot<HamsterBot, HamsterBotSettings> {

    private final BinanceService binanceService;

    @Autowired
    public HamsterBot(HamsterBotSettings settings, BinanceService binanceService) {
        super(settings, HamsterBot.class);
        this.binanceService = binanceService;
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
        if (message.isChannelMessage()) {
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
        }
    }

    private void executeCommand(String userId, String login, Long chatId, BotCommand<CommandName> command) throws IOException {
        switch (command.name()) {
            case GET_ALL_EXCHANGE_PAIRS:
                sendAllExchangePairsKeyboard(chatId);
                break;
            case GET_EXCHANGE_PAIR_AVG_PRICE_INFO:
                sendExchangePairAvgPriceInfo(chatId, command.arg(0).orElse("BTCUSDT").toUpperCase());
                break;
            default:
                log.error("Unsupported command: command={}", command);
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
