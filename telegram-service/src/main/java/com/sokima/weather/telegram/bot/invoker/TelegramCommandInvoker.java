package com.sokima.weather.telegram.bot.invoker;

import com.sokima.weather.telegram.bot.command.TelegramCommand;
import com.sokima.weather.telegram.bot.command.flagable.FlagableTelegramCommand;
import com.sokima.weather.telegram.domain.UserCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

@Slf4j
@Component
public class TelegramCommandInvoker {

    @Autowired
    @Qualifier("commandMap")
    private Map<String, TelegramCommand> telegramCommandMap;

    public String invokeCommand(UserCommand userCommand, Message message) {
        String command = userCommand.command();
        TelegramCommand telegramCommand = telegramCommandMap.get(command);
        telegramCommand.setMessage(message);

        if (userCommand.hasFlags() &&
                (telegramCommand instanceof FlagableTelegramCommand flagableTelegramCommand)) {
            flagableTelegramCommand.setFlags(userCommand.flags());
        }

        String result;
        if (userCommand.hasParams()) {
            result = telegramCommand.execute(userCommand.params());
            log.info("Command[{}] with params was invoked.", command);
        } else {
            result = telegramCommand.execute();
            log.info("Command[{}] was invoked.", command);
        }
        return result;
    }
}
