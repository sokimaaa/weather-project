package com.sokima.weather.telegram.exception;

public class MessageAcquiringException extends RuntimeException {
    public MessageAcquiringException() {
    }

    public MessageAcquiringException(String message) {
        super(message);
    }

    public MessageAcquiringException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageAcquiringException(Throwable cause) {
        super(cause);
    }

    public MessageAcquiringException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
