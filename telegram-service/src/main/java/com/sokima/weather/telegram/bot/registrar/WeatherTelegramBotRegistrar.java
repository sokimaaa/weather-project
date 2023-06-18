package com.sokima.weather.telegram.bot.registrar;

import com.sokima.weather.telegram.bot.WeatherTelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Primary
@Component
@RequiredArgsConstructor
public class WeatherTelegramBotRegistrar implements TelegramBotRegistrar {

    private boolean isInstantiated = false;

    @Value("${weather.telegram.bot.reconnect-pause}")
    private int awaitInMillis;

    private final TelegramBotsApi telegramBotsApi;

    private final WeatherTelegramBot weatherTelegramBot;

    public synchronized void registerBot() {
        if (isInstantiated) {
            return;
        }

        try {
            telegramBotsApi.registerBot(weatherTelegramBot);
            isInstantiated = true;
            log.info("{} Bot was registered.", weatherTelegramBot.getClass().getName());
        } catch (TelegramApiException ex) {
            try {
                Thread.sleep(awaitInMillis);
            } catch (InterruptedException e) {
                log.warn("{} thread was interrupted.", Thread.currentThread().getName());
                Thread.currentThread().interrupt();
            }
            log.error("Fail to register {} bot : {}", weatherTelegramBot.getClass().getName(), ex.getMessage());
            registerBot();
        }
    }
}
