package com.sokima.weather.api.exception;


import com.sokima.weather.api.service.client.WeatherWebClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;

/**
 * Occurs when have fatal error in {@link WeatherWebClient}.
 * Possible scenarios:
 * - Invalid Token
 * - Token Expired
 * - Required params are not provided
 * - Token has exceeded amount of call per month
 */
public class WeatherWebClientException extends RuntimeException {

    private static final String ERROR_MESSAGE_400 = "API request url is invalid or Required parameters not provided.";
    private static final String ERROR_MESSAGE_401 = "Token is not provided.";
    private static final String ERROR_MESSAGE_403 = "Token has been disabled or invalid.";
    private static final String ERROR_MESSAGE_DEFAULT = "Unknown Error Code.";

    public WeatherWebClientException() {

    }

    public WeatherWebClientException(String message) {
        super(message);
    }

    public WeatherWebClientException(HttpStatus code) {
        this(getErrorMessageForHttpStatus(code));
    }

    public WeatherWebClientException(ClientResponse response) {
        this(getErrorMessageForHttpStatus(response.statusCode()));
    }

    private static String getErrorMessageForHttpStatus(HttpStatus code) {
        int value = code.value();
        final String errorMessage;
        switch (value) {
            case 400 -> errorMessage = ERROR_MESSAGE_400;
            case 401 -> errorMessage = ERROR_MESSAGE_401;
            case 403 -> errorMessage = ERROR_MESSAGE_403;
            default -> errorMessage = ERROR_MESSAGE_DEFAULT;
        }

        return errorMessage;
    }
}
