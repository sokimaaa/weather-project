package com.sokima.weather.api.service.client;

import com.sokima.weather.api.configuration.WeatherConfiguration;
import com.sokima.weather.api.domain.RealtimeWeatherResponse;
import com.sokima.weather.api.domain.WeatherQueryType;
import com.sokima.weather.api.exception.WeatherWebClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
public class WeatherWebClient extends AbstractWebClient {

    private static final String REALTIME_URL = "/current.json";
    private static final String DEFAULT_LANG = "en";

    private final WebClient webClient;
    private final WeatherConfiguration weatherConfiguration;

    public WeatherWebClient(WeatherConfiguration weatherConfiguration) {
        this.weatherConfiguration = weatherConfiguration;
        this.webClient = WebClient.builder()
                .baseUrl(weatherConfiguration.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, JSON_TYPE)
                .filter(logRequestFilter())
                .build()
        ;
    }

    public Mono<RealtimeWeatherResponse> getRealtimeWeatherData(String query) {
        log.info("Weather Query type is: {}", WeatherQueryType.getQueryType(query));
        return webClient.get()
                .uri(realtimeUrl(query))
                .retrieve()
                .onStatus(
                        HttpStatus::is4xxClientError,
                        response -> Mono.error(
                                () -> new WeatherWebClientException(response)
                        )
                )
                .bodyToMono(RealtimeWeatherResponse.class);
    }

    private String realtimeUrl(String query, String lang, String token) {
        return UriComponentsBuilder.fromUriString(REALTIME_URL)
                .queryParam("key", token)
                .queryParam("q", query)
                .queryParam("lang", lang)
                .toUriString();
    }

    private String realtimeUrl(String query, String lang) {
        return realtimeUrl(query, lang, weatherConfiguration.getToken());
    }

    private String realtimeUrl(String query) {
        final String lang = weatherConfiguration.getRealtime().getLang();
        return realtimeUrl(
                query,
                Objects.isNull(lang) || lang.isBlank() ? DEFAULT_LANG : lang
        );
    }
}
