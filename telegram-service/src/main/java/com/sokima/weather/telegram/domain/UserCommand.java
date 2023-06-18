package com.sokima.weather.telegram.domain;

import java.util.Arrays;

public record UserCommand(
        String command,
        String[] flags,
        String[] params
) {
    public static UserCommand create(String command, String[] flags, String[] params) {
        return new UserCommand(command, flags, params);
    }

    public static UserCommand create(String command) {
        return new UserCommand(command, new String[0], new String[0]);
    }

    public static UserCommand empty() {
        return create("");
    }

    public boolean hasParams() {
        return params.length != 0;
    }

    public boolean hasFlags() {
        return flags.length != 0;
    }

    @Override
    public String toString() {
        return "UserCommand[" + "command=" + command +
                ", flags=" + Arrays.toString(flags) +
                ", params=" + Arrays.toString(params) + ']';
    }
}
