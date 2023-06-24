package com.sokima.weather.api.configuration;

import com.sokima.weather.api.exception.GeographyConfigurationException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Configuration
@ConfigurationProperties(prefix = "geography.api")
@Getter
@Setter
public class GeographyConfiguration {

    private String token;

    private String baseUrl;

    @PostConstruct
    public void validate() {
        if (Objects.isNull(token) || Objects.isNull(baseUrl)) {
            throw new GeographyConfigurationException("Necessary configuration were not set.");
        }
    }
}
