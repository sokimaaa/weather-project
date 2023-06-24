package com.sokima.weather.telegram.bot.command.output;

import java.time.Instant;

public class AsyncCommandOutput extends AbstractCommandOutput<Instant> {
    private AsyncCommandOutput(Instant output) {
        super(output);
    }

    public static AsyncCommandOutput create() {
        return new AsyncCommandOutput(Instant.now());
    }
}
