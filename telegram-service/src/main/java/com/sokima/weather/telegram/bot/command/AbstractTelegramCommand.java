package com.sokima.weather.telegram.bot.command;

import com.sokima.weather.telegram.exception.UnsupportedCommandException;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
public abstract class AbstractTelegramCommand implements TelegramCommand {

    protected Message message;

    @Override
    public String execute() {
        log.error("Command[{}] can't be executed without params.", command());
        throw new UnsupportedCommandException("Try to execute command without params.");
    }

    @Override
    public String execute(String[] params) {
        log.error("Command[{}] can't be executed with params.", command());
        throw new UnsupportedCommandException("Try to execute command with params.");
    }

    @Override
    public void setMessage(Message message) {
        this.message = message;
    }
}
