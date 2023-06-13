package com.sokima.weather.api.exception;

public class WeatherConfigurationException extends RuntimeException {

    public WeatherConfigurationException() {
    }

    public WeatherConfigurationException(String message) {
        super(message);
    }

    public WeatherConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeatherConfigurationException(Throwable cause) {
        super(cause);
    }

    public WeatherConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
