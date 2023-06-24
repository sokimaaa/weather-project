package com.sokima.weather.api.service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

@Slf4j
public abstract class AbstractWebClient {

    protected static final String JSON_TYPE = MediaType.APPLICATION_JSON_VALUE;

    protected ExchangeFilterFunction logRequestFilter() {
        return (clientRequest, next) -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach(
                    (name, values) -> values.forEach(value -> log.info("{}={}", name, value))
            );
            return next.exchange(clientRequest);
        };
    }
}
