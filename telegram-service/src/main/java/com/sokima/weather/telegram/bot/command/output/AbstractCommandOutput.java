package com.sokima.weather.telegram.bot.command.output;

public abstract class AbstractCommandOutput<O> implements CommandOutput<O> {

    protected O output;

    protected AbstractCommandOutput(O output) {
        this.output = output;
    }

    @Override
    public O getResult() {
        return output;
    }
}
