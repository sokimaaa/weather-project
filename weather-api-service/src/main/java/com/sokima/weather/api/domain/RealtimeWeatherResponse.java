package com.sokima.weather.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RealtimeWeatherResponse(

        @JsonProperty("location") LocationJson location,

        @JsonProperty("current") RealtimeWeatherJson realtimeWeather
) {
}
