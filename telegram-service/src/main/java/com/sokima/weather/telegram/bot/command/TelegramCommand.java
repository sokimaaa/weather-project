package com.sokima.weather.telegram.bot.command;

import org.telegram.telegrambots.meta.api.objects.Message;

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
     * @return string
     */
    String execute();

    /**
     * Does logic for command and returns text for user.
     *
     * @return string
     */
    String execute(String[] params);

    /**
     * @param message the context for execution
     */
    void setMessage(Message message);
}
