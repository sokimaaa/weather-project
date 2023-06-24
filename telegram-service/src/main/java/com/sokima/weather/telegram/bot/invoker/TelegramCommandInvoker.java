package com.sokima.weather.telegram.bot.invoker;

import com.sokima.weather.telegram.bot.command.TelegramCommand;
import com.sokima.weather.telegram.bot.command.flagable.FlagableTelegramCommand;
import com.sokima.weather.telegram.bot.command.output.CommandOutput;
import com.sokima.weather.telegram.domain.UserCommand;
import com.sokima.weather.telegram.domain.UserQueryHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class TelegramCommandInvoker {

    @Autowired
    @Qualifier("commandMap")
    private Map<String, TelegramCommand> telegramCommandMap;

    public CommandOutput<?> invokeCommand(UserCommand userCommand, UserQueryHolder holder) {
        String command = userCommand.command();
        TelegramCommand telegramCommand = telegramCommandMap.get(command);
        telegramCommand.setContext(holder);

        if (userCommand.hasFlags() &&
                (telegramCommand instanceof FlagableTelegramCommand flagableTelegramCommand)) {
            flagableTelegramCommand.setFlags(userCommand.flags());
        }

        CommandOutput<?> output;
        if (userCommand.hasParams()) {
            output = telegramCommand.execute(userCommand.params());
            log.info("Command[{}] with params was invoked.", command);
        } else {
            output = telegramCommand.execute();
            log.info("Command[{}] was invoked.", command);
        }

        return output;
    }
}
