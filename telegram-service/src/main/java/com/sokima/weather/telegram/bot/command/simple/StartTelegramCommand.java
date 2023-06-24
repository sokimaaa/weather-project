package com.sokima.weather.telegram.bot.command.simple;

import com.sokima.weather.telegram.bot.command.AbstractTelegramCommand;
import com.sokima.weather.telegram.bot.command.TelegramCommand;
import com.sokima.weather.telegram.bot.command.output.CommandOutput;
import com.sokima.weather.telegram.bot.command.output.SingleStringCommandOutput;
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
    public CommandOutput<?> execute() {
        String startMessage = new StringBuilder()
                .append("Hello ")
                .append(context.message().getFrom().getUserName())
                .append("! :)\n\n")
                .append("I am the SokimaWeatherBot and ")
                .append("could be helpful with understanding weather in different cities.\n\n")
                .append("Feel free to ask anything ;)")
                .toString();
        return SingleStringCommandOutput.create(startMessage);
    }
}
