package com.sokima.weather.telegram.exception;

public class TelegramConfigurationException extends RuntimeException {
    public TelegramConfigurationException() {
    }

    public TelegramConfigurationException(String message) {
        super(message);
    }

    public TelegramConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TelegramConfigurationException(Throwable cause) {
        super(cause);
    }

    public TelegramConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
