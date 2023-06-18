package com.sokima.weather.telegram.exception;

public class TelegramCommandConfigurationException extends RuntimeException {
    public TelegramCommandConfigurationException() {
    }

    public TelegramCommandConfigurationException(String message) {
        super(message);
    }

    public TelegramCommandConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TelegramCommandConfigurationException(Throwable cause) {
        super(cause);
    }

    public TelegramCommandConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
