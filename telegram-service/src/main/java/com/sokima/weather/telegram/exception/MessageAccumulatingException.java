package com.sokima.weather.telegram.exception;

public class MessageAccumulatingException extends RuntimeException {
    public MessageAccumulatingException() {
    }

    public MessageAccumulatingException(String message) {
        super(message);
    }

    public MessageAccumulatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageAccumulatingException(Throwable cause) {
        super(cause);
    }

    public MessageAccumulatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
