package com.sokima.weather.telegram.domain;

public enum ActionEnum {
    /**
     * enters text, voice or document message
     */
    IGNORE,

    /**
     * enters command
     */
    COMMAND,

    /**
     * enters command in wrong way, e.g.
     * 1. wrong amount of params, flags
     * 2. wrong params, flags
     * and so on
     */
    WRONG_COMMAND,

    /**
     * enters wrong command:
     * - command to not our bot
     * - command does not support yet or exist
     */
    UNRECOGNIZED_COMMAND
}
