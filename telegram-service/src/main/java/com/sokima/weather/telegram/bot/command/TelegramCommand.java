package com.sokima.weather.telegram.bot.command;

import com.sokima.weather.telegram.bot.command.output.CommandOutput;
import com.sokima.weather.telegram.domain.UserQueryHolder;

public interface TelegramCommand {

    /**
     * Command to trigger action.
     * Should start with forward slash (/).
     *
     * @return string
     */
    String command();

    /**
     * Does logic for command and returns text for user.
     *
     * @return {@link CommandOutput}
     */
    CommandOutput<?> execute();

    /**
     * Does logic for command and returns text for user.
     *
     * @return {@link CommandOutput}
     */
    CommandOutput<?> execute(String[] params);

    /**
     * @param holder the context for execution
     */
    void setContext(UserQueryHolder holder);
}
