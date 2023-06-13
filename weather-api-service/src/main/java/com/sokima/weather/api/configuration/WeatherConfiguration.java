package com.sokima.weather.api.configuration;

import com.sokima.weather.api.exception.WeatherConfigurationException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Configuration
@ConfigurationProperties(prefix = "weather.api")
@Getter
@Setter
public class WeatherConfiguration {

    private String token;

    private String baseUrl;

    private RealtimeWeatherConfiguration realtime = new RealtimeWeatherConfiguration();

    @Getter
    @Setter
    public static class RealtimeWeatherConfiguration {

        private String lang;
    }

    @PostConstruct
    public void validate() {
        if (Objects.isNull(token) || Objects.isNull(baseUrl)) {
            throw new WeatherConfigurationException("Necessary configuration were not set.");
        }
    }
}
