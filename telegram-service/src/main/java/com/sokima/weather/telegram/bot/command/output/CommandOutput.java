package com.sokima.weather.telegram.bot.command.output;

public interface CommandOutput<O> {
    O getResult();

    default boolean isMultipleOutput() {
        return this instanceof MultipleCommandOutput<?>;
    }

    default boolean isSingleOutput() {
        return this instanceof SingleCommandOutput<?>;
    }

    default boolean isThrowableOutput() {
        return this instanceof ThrowableCommandOutput;
    }

    default boolean isAsyncOutput() {
        return this instanceof AsyncCommandOutput;
    }
}
