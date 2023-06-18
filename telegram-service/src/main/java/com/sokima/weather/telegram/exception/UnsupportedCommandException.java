package com.sokima.weather.telegram.exception;

public class UnsupportedCommandException extends RuntimeException {
    public UnsupportedCommandException() {
    }

    public UnsupportedCommandException(String message) {
        super(message);
    }

    public UnsupportedCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedCommandException(Throwable cause) {
        super(cause);
    }

    public UnsupportedCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
