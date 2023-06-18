package com.sokima.weather.telegram.configuration;

import com.sokima.weather.telegram.bot.WeatherTelegramBot;
import com.sokima.weather.telegram.bot.registrar.TelegramBotRegistrar;
import com.sokima.weather.telegram.service.accumulator.CommandMessageAccumulator;
import com.sokima.weather.telegram.service.accumulator.FailMessageAccumulator;
import com.sokima.weather.telegram.service.parser.GeneralUpdateParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Configuration
public class TelegramBotConfiguration {

    @Value("${weather.telegram.bot.reconnect-pause}")
    private int awaitInMillis;

    @Bean
    public WeatherTelegramBot weatherTelegramBot(GeneralUpdateParser generalUpdateParser, CommandMessageAccumulator commandMessageAccumulator, FailMessageAccumulator failMessageAccumulator, BotConfiguration configuration) {
        final String username = configuration.getUsername();
        final String token = configuration.getToken();
        return new WeatherTelegramBot(generalUpdateParser, commandMessageAccumulator, failMessageAccumulator, username, token);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        try {
            return new TelegramBotsApi(DefaultBotSession.class);
        } catch (TelegramApiException ex) {
            try {
                Thread.sleep(awaitInMillis);
            } catch (InterruptedException e) {
                log.warn("{} thread was interrupted.", Thread.currentThread().getName());
                Thread.currentThread().interrupt();
            }
            log.error("Fail to create TelegramBotsApi : {}", ex.getMessage());
            return telegramBotsApi();
        }
    }

    @Component
    @RequiredArgsConstructor
    public static class TelegramBotRegistrationManager {

        private final TelegramBotRegistrar telegramBotRegistrar;

        @EventListener(ContextRefreshedEvent.class)
        public void registerLongPollingBots() {
            telegramBotRegistrar.registerBot();
        }
    }
}
