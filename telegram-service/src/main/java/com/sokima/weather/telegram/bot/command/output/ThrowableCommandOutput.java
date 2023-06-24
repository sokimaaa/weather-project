package com.sokima.weather.telegram.bot.command.output;

public class ThrowableCommandOutput extends SingleCommandOutput<RuntimeException> {
    private ThrowableCommandOutput(RuntimeException output) {
        super(output);
    }

    public static ThrowableCommandOutput create(RuntimeException exception) {
        return new ThrowableCommandOutput(exception);
    }

    @Override
    public RuntimeException getResult() {
        throw output;
    }
}
