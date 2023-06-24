package com.sokima.weather.telegram.bot.command;

import com.sokima.weather.telegram.bot.command.output.CommandOutput;
import com.sokima.weather.telegram.bot.command.output.ThrowableCommandOutput;
import com.sokima.weather.telegram.domain.UserQueryHolder;
import com.sokima.weather.telegram.exception.UnsupportedCommandException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTelegramCommand implements TelegramCommand {

    protected UserQueryHolder context;

    @Override
    public CommandOutput<?> execute() {
        log.error("Command[{}] can't be executed without params.", command());
        UnsupportedCommandException exception = new UnsupportedCommandException("Try to execute command without params.");
        return ThrowableCommandOutput.create(exception);
    }

    @Override
    public CommandOutput<?> execute(String[] params) {
        log.error("Command[{}] can't be executed with params.", command());
        UnsupportedCommandException exception = new UnsupportedCommandException("Try to execute command with params.");
        return ThrowableCommandOutput.create(exception);
    }

    @Override
    public void setContext(UserQueryHolder context) {
        this.context = context;
    }
}
