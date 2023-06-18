package com.sokima.weather.telegram.configuration;

import com.sokima.weather.telegram.bot.command.TelegramCommand;
import com.sokima.weather.telegram.exception.TelegramCommandConfigurationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Configuration
public class TelegramCommandConfiguration {
    @Bean
    @Qualifier("commandMap")
    public Map<String, TelegramCommand> commandMap(List<TelegramCommand> telegramCommandList) {
        return telegramCommandList.stream().collect(Collectors.toMap(
                TelegramCommand::command,
                v -> v,
                (k, v) -> {
                    final String errorMsg = format("Duplicates TelegramCommand's for command : %s", k.command());
                    throw new TelegramCommandConfigurationException(errorMsg);
                }
        ));
    }

    @Bean
    @Qualifier("commands")
    public List<String> commands(List<TelegramCommand> telegramCommandList) {
        return telegramCommandList.stream()
                .map(TelegramCommand::command)
                .toList();
    }
}

