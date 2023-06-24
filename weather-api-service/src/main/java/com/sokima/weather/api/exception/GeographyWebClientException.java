package com.sokima.weather.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;

public class GeographyWebClientException extends RuntimeException {

    private static final String ERROR_MESSAGE_400 = "Missing a required parameter.";
    private static final String ERROR_MESSAGE_401 = "No valid API key provided.";
    private static final String ERROR_MESSAGE_404 = "The requested resource doesn't exist.";
    private static final String ERROR_MESSAGE_429 = "API request limit exceeded.";
    private static final String ERROR_MESSAGE_5XX = "API failed to process request.";

    public GeographyWebClientException() {
    }

    public GeographyWebClientException(String message) {
        super(message);
    }

    public GeographyWebClientException(HttpStatus code) {
        this(getErrorMessageForHttpStatus(code));
    }

    public GeographyWebClientException(ClientResponse response) {
        this(getErrorMessageForHttpStatus(response.statusCode()));
    }

    private static String getErrorMessageForHttpStatus(HttpStatus code) {
        int value = code.value();
        final String errorMessage;
        switch (value) {
            case 400 -> errorMessage = ERROR_MESSAGE_400;
            case 401 -> errorMessage = ERROR_MESSAGE_401;
            case 404 -> errorMessage = ERROR_MESSAGE_404;
            case 429 -> errorMessage = ERROR_MESSAGE_429;
            default -> errorMessage = ERROR_MESSAGE_5XX;
        }

        return errorMessage;
    }
}
