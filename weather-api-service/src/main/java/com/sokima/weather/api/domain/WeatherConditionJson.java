package com.sokima.weather.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherConditionJson(

        /**
         * Weather condition text.
         */
        @JsonProperty("text") String condition,

        /**
         * Weather icon url.
         */
        @JsonProperty("icon") String iconUrl,

        /**
         * Weather icon url.
         */
        @JsonProperty("code") String code
) {
}
