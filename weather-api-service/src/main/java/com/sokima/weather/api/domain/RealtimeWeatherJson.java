package com.sokima.weather.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RealtimeWeatherJson(

        /**
         * Local time when the real time data was updated.
         */
        @JsonProperty("last_updated") String lastUpdated,

        /**
         * Temperature in celsius.
         */
        @JsonProperty("temp_c") Float temperatureCelsius,

        /**
         * Temperature in fahrenheit.
         */
        @JsonProperty("temp_f") Float temperatureFahrenheit,

        /**
         * Feels like temperature in celsius.
         */
        @JsonProperty("feelslike_c") Float feelsLikeCelsius,

        /**
         * 	Feels like temperature in fahrenheit.
         */
        @JsonProperty("feelslike_f") Float feelsLikeFahrenheit,

        /**
         *  Weather condition text, icon url, unique code.
         */
        @JsonProperty("condition") WeatherConditionJson weatherCondition,

        /**
         * Wind speed in miles per hour.
         */
        @JsonProperty("wind_mph") Float windMph,

        /**
         * Wind speed in kilometer per hour.
         */
        @JsonProperty("wind_kph") Float windKph,

        /**
         * 	Wind direction in degrees.
         */
        @JsonProperty("wind_degree") Integer windDegree,

        /**
         * Wind direction as 16 point compass. e.g.: NSW.
         */
        @JsonProperty("wind_dir") String windDirection,

        /**
         * Pressure in millibars.
         */
        @JsonProperty("pressure_mb") Float pressureMb,

        /**
         * Pressure in inches.
         */
        @JsonProperty("pressure_in") Float pressureIn,

        /**
         * Precipitation amount in millimeters.
         */
        @JsonProperty("precip_mm") Float precipitationMm,

        /**
         * Precipitation amount in inches.
         */
        @JsonProperty("precip_in") Float PrecipitationIn,

        /**
         * Humidity as percentage.
         */
        @JsonProperty("humidity") Integer humidity,

        /**
         * Cloud cover as percentage.
         */
        @JsonProperty("cloud") Integer cloud,

        /**
         * 1 = Yes 0 = No
         * Whether to show day condition icon or night icon.
         */
        @JsonProperty("is_day") Integer isDay,

        /**
         * UV Index.
         */
        @JsonProperty("uv") Float uv,

        /**
         * Wind gust in miles per hour.
         */
        @JsonProperty("gust_mph") Float gustMph,

        /**
         * Wind gust in kilometer per hour.
         */
        @JsonProperty("gust_kph") Float gustKph
) {
}
