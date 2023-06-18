package com.sokima.weather.telegram.configuration;

import com.sokima.weather.telegram.exception.TelegramConfigurationException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Configuration
@ConfigurationProperties(prefix = "weather.telegram.bot")
@Getter
@Setter
public class BotConfiguration {

    private String username;

    private String token;

    @PostConstruct
    public void validate() {
        if (Objects.isNull(username) || Objects.isNull(token)) {
            throw new TelegramConfigurationException("Necessary configurations were not set.");
        }
    }
}
