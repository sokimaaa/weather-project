package com.sokima.weather.api.exception;

public class GeographyConfigurationException extends RuntimeException {

    public GeographyConfigurationException() {
    }

    public GeographyConfigurationException(String message) {
        super(message);
    }

    public GeographyConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeographyConfigurationException(Throwable cause) {
        super(cause);
    }

    public GeographyConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
