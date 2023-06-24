package com.sokima.weather.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MajorCityResponse(
        @JsonProperty("country") CountryJson countryJson,
        @JsonProperty("geo_id") Long geoId,
        @JsonProperty("latitude") Float latitude,
        @JsonProperty("longitude") Float longitude,
        @JsonProperty("name") String name,
        @JsonProperty("state_or_region") String stateOrRegion
) {
}
