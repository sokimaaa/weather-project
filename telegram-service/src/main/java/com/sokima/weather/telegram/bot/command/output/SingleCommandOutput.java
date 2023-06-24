package com.sokima.weather.telegram.bot.command.output;

public abstract class SingleCommandOutput<O> extends AbstractCommandOutput<O> {
    protected SingleCommandOutput(O output) {
        super(output);
    }
}
