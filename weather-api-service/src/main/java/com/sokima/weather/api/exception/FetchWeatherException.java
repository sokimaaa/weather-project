package com.sokima.weather.api.exception;

public class FetchWeatherException extends RuntimeException {
    public FetchWeatherException() {
    }

    public FetchWeatherException(String message) {
        super(message);
    }

    public FetchWeatherException(String message, Throwable cause) {
        super(message, cause);
    }

    public FetchWeatherException(Throwable cause) {
        super(cause);
    }

    public FetchWeatherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
