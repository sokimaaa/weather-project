package com.sokima.weather.telegram.bot.command.simple;

import com.sokima.weather.telegram.bot.command.AbstractTelegramCommand;
import com.sokima.weather.telegram.bot.command.TelegramCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartTelegramCommand extends AbstractTelegramCommand implements TelegramCommand {

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String execute() {
        return new StringBuilder()
                .append("Hello ")
                .append(message.getFrom().getUserName())
                .append("! :)\n\n")
                .append("I am the SokimaWeatherBot and ")
                .append("could be helpful with understanding weather in different cities.\n\n")
                .append("Feel free to ask anything ;)")
                .toString();
    }
}
