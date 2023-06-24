package com.sokima.weather.api.service.client;

import com.sokima.weather.api.configuration.GeographyConfiguration;
import com.sokima.weather.api.domain.MajorCityResponse;
import com.sokima.weather.api.exception.GeographyWebClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GeographyWebClient extends AbstractWebClient {

    private static final String AUTH_HEADER = "apiKey";

    private static final String MAJOR_CITY_URL = "/country/cities/{country}";

    private final WebClient webClient;
    private final GeographyConfiguration geographyConfiguration;

    public GeographyWebClient(GeographyConfiguration geographyConfiguration) {
        this.geographyConfiguration = geographyConfiguration;
        this.webClient = WebClient.builder()
                .baseUrl(geographyConfiguration.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, JSON_TYPE)
                .defaultHeader(AUTH_HEADER, geographyConfiguration.getToken())
                .filter(logRequestFilter())
                .build()
        ;
    }

    public Flux<MajorCityResponse> getMajorCityData(String query) {
        log.info("Geographic Api trying to get major cities by : {} code", query);
        return webClient.get()
                .uri(majorCityUrl(query))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        HttpStatus::is4xxClientError,
                        response -> Mono.error(
                                () -> new GeographyWebClientException(response)
                        )
                )
                .bodyToFlux(MajorCityResponse.class);
    }

    private String majorCityUrl(String query) {
        return UriComponentsBuilder.fromUriString(MAJOR_CITY_URL)
                .buildAndExpand(query)
                .toUriString();
    }
}
