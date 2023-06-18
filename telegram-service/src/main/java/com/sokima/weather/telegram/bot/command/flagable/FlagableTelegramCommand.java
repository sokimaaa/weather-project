package com.sokima.weather.telegram.bot.command.flagable;

import com.sokima.weather.telegram.bot.command.TelegramCommand;

import java.util.List;

public interface FlagableTelegramCommand extends TelegramCommand, Flagable {
    default List<String> commands() {
        return flags().stream().map(flag -> command().concat(" ").concat(flag)).toList();
    }

    void setFlags(String[] flags);
}
