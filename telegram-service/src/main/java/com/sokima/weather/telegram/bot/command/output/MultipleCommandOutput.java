package com.sokima.weather.telegram.bot.command.output;

public abstract class MultipleCommandOutput<O> extends AbstractCommandOutput<Iterable<O>> {
    protected MultipleCommandOutput(Iterable<O> output) {
        super(output);
    }
}
