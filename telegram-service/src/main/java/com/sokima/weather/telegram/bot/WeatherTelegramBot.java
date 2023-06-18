package com.sokima.weather.telegram.bot;

import com.sokima.weather.telegram.domain.ActionEnum;
import com.sokima.weather.telegram.domain.UserQueryHolder;
import com.sokima.weather.telegram.service.accumulator.CommandMessageAccumulator;
import com.sokima.weather.telegram.service.accumulator.FailMessageAccumulator;
import com.sokima.weather.telegram.service.parser.GeneralUpdateParser;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class WeatherTelegramBot extends TelegramLongPollingBot {

    private final GeneralUpdateParser generalUpdateParser;
    private final CommandMessageAccumulator commandMessageAccumulator;
    private final FailMessageAccumulator failMessageAccumulator;
    private final String username;

    public WeatherTelegramBot(GeneralUpdateParser generalUpdateParser, CommandMessageAccumulator commandMessageAccumulator, FailMessageAccumulator failMessageAccumulator, String username, String token) {
        super(token);
        this.generalUpdateParser = generalUpdateParser;
        this.commandMessageAccumulator = commandMessageAccumulator;
        this.failMessageAccumulator = failMessageAccumulator;
        this.username = username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        UserQueryHolder holder = generalUpdateParser.parse(update);
        ActionEnum actionEnum = holder.actionEnum();

        switch (actionEnum) {
            case COMMAND -> {
                log.info("Action[{}] was detected and delivered to execution.", actionEnum);
                commandMessageAccumulator.accumulate(holder);
            }
            case WRONG_COMMAND -> {
                log.info("Action[{}] was detected and rejected execution with notification.", actionEnum);
                failMessageAccumulator.accumulate(holder);
            }
            case UNRECOGNIZED_COMMAND -> log.info("Action[{}] was detected and rejected execution.", actionEnum);
            case IGNORE -> log.info("Action[{}] was detected and ignored.", actionEnum);
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
