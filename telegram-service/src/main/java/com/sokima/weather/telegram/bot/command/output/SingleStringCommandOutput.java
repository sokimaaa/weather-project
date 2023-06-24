package com.sokima.weather.telegram.bot.command.output;

public class SingleStringCommandOutput extends SingleCommandOutput<String> {
    private SingleStringCommandOutput(String output) {
        super(output);
    }

    public static SingleStringCommandOutput create(String output) {
        return new SingleStringCommandOutput(output);
    }
}
