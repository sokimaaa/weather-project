package com.sokima.weather.api.service;

import com.sokima.weather.api.domain.MajorCityResponse;
import com.sokima.weather.api.service.client.GeographyWebClient;
import com.sokima.weather.proto.RegionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeographyService {

    private final GeographyWebClient geographyWebClient;

    public Flux<MajorCityResponse> getMajorCities(RegionRequest request) {
        Flux<MajorCityResponse> majorCityData = Flux.empty();
        if (request.hasCountry()) {
            String countryCode = request.getCountry();
            log.info("Getting major cities by country code : {}", countryCode);
            majorCityData = geographyWebClient.getMajorCityData(countryCode);
        }

        if (request.hasRegion()) {
            String region = request.getRegion();
            log.warn("Getting major cities by region : {}", region);
            throw new UnsupportedOperationException("Getting major cities by region are not supported yet.");
        }

        return majorCityData;
    }
}
