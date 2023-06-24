package com.sokima.weather.telegram.bot.command.flagable;

import com.sokima.weather.telegram.bot.command.AbstractTelegramCommand;

import java.util.Arrays;

public abstract class AbstractFlagableTelegramCommand extends AbstractTelegramCommand implements FlagableTelegramCommand {

    private static final String BLANK = "";
    private String[] flags = new String[0];

    @Override
    public void setFlags(String[] flags) {
        this.flags = flags;
    }

    protected String flag() {
        return Arrays.stream(flags)
                .filter(flag -> flags().contains(flag))
                .findFirst()
                .orElse(BLANK);
    }
}
