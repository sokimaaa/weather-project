package com.sokima.weather.telegram.bot.command.flagable;

import java.util.List;

public interface Flagable {

    /**
     * @return immutable list of supported flags
     */
    List<String> flags();
}
