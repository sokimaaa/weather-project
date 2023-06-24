package com.sokima.weather.telegram.bot.command.simple;

import com.sokima.weather.telegram.bot.command.AbstractTelegramCommand;
import com.sokima.weather.telegram.bot.command.TelegramCommand;
import com.sokima.weather.telegram.bot.command.flagable.FlagableTelegramCommand;
import com.sokima.weather.telegram.bot.command.output.CommandOutput;
import com.sokima.weather.telegram.bot.command.output.SingleStringCommandOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class HelpTelegramCommand extends AbstractTelegramCommand implements TelegramCommand {

    @Value("${weather.support.contact}")
    private String contact;

    private final List<String> commands;

    private final List<FlagableTelegramCommand> flagableTelegramCommands;

    @Autowired
    public HelpTelegramCommand(List<FlagableTelegramCommand> flagableTelegramCommands,
                               @Lazy @Qualifier("commands") List<String> commands) {
        this.flagableTelegramCommands = flagableTelegramCommands;
        this.commands = commands;
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public CommandOutput<?> execute() {
        String helpMessage = appendWithFlaggedCommands(
                appendWithCommands(
                        new StringBuilder()
                                .append("I can help you with understanding Weather in different country.\n\n")
                                .append("Here is the list of my commands: \n")
                )
        )
                .append("\nHope this was hopeful ;)\n\n")
                .append("For getting more detailed info you can contact the developer teams\n")
                .append("Email : ")
                .append(contact)
                .toString();
        return SingleStringCommandOutput.create(helpMessage);
    }

    private StringBuilder appendWithCommands(StringBuilder stringBuilder) {
        stringBuilder.append("===== Basic Commands =====\n");
        for (String command : commands) {
            stringBuilder.append("| ")
                    .append(command)
                    .append(" |\n");
        }
        stringBuilder.append('\n');
        return stringBuilder;
    }

    private StringBuilder appendWithFlaggedCommands(StringBuilder stringBuilder) {
        stringBuilder.append("===== Commands with Flags =====\n");
        for (FlagableTelegramCommand flagable : flagableTelegramCommands) {
            stringBuilder.append("--- Command: ").append(flagable.command()).append(" ---\n");
            for (String flaggedCommand : flagable.commands()) {
                stringBuilder.append("| ").append(flaggedCommand).append(" |\n");
            }
        }
        stringBuilder.append('\n');
        return stringBuilder;
    }
}
