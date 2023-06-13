package com.sokima.weather.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LocationJson(

        /**
         * Location name.
         */
        @JsonProperty("name") String name,

        /**
         * Region or state of the location, if available.
         */
        @JsonProperty("region") String region,

        /**
         * Location country.
         */
        @JsonProperty("country") String country,

        /**
         * Latitude in decimal degree.
         */
        @JsonProperty("lat") Float latitude,

        /**
         * Longitude in decimal degree.
         */
        @JsonProperty("lon") Float longitude,

        /**
         * Time zone name.
         */
        @JsonProperty("tz_id") String timezone,

        /**
         * Local date and time.
         */
        @JsonProperty("localtime") String localtime
) {
}
