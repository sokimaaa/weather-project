package com.sokima.weather.telegram.service.parser;

import com.sokima.weather.telegram.domain.ActionEnum;
import com.sokima.weather.telegram.domain.UserCommand;
import com.sokima.weather.telegram.domain.UserQueryHolder;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public abstract class AbstractUpdateParser {

    public UserQueryHolder parse(Update update) {
        Message message = parseMessage(update);
        UserCommand userCommand = parseCommand(message.getText());
        ActionEnum actionEnum = parseAction(userCommand);

        log.info("Update[{}] was parsed.", update.getUpdateId());
        return UserQueryHolder.create(message, userCommand, actionEnum);
    }

    protected Message parseMessage(Update update) {
        return update.getMessage();
    }

    protected abstract UserCommand parseCommand(String text);
    protected abstract ActionEnum parseAction(UserCommand userCommand);
}
