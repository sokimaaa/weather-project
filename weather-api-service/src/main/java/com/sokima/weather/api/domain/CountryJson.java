package com.sokima.weather.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CountryJson(
        @JsonProperty("code") String code,
        @JsonProperty("name") String name
) {
}
