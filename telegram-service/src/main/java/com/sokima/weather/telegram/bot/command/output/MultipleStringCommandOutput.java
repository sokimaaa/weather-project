package com.sokima.weather.telegram.bot.command.output;

public class MultipleStringCommandOutput extends MultipleCommandOutput<String> {
    private MultipleStringCommandOutput(Iterable<String> output) {
        super(output);
    }

    public static MultipleStringCommandOutput create(Iterable<String> output) {
        return new MultipleStringCommandOutput(output);
    }
}
