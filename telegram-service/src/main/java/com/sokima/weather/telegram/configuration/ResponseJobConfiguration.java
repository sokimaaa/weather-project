package com.sokima.weather.telegram.configuration;

import com.sokima.weather.telegram.bot.WeatherTelegramBot;
import com.sokima.weather.telegram.bot.invoker.TelegramCommandInvoker;
import com.sokima.weather.telegram.domain.UserQueryHolder;
import com.sokima.weather.telegram.service.WeatherThreadPool;
import com.sokima.weather.telegram.service.accumulator.CommandMessageAccumulator;
import com.sokima.weather.telegram.service.accumulator.FailMessageAccumulator;
import com.sokima.weather.telegram.service.job.*;
import com.sokima.weather.telegram.service.job.creator.ResponseJobCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ResponseJobConfiguration {
    @Bean
    public ResponseJobCreator responseJobCreator(WeatherThreadPool weatherThreadPool,
                                                 CommandMessageAccumulator commandMessageAccumulator,
                                                 FailMessageAccumulator failMessageAccumulator,
                                                 TelegramCommandInvoker telegramCommandInvoker,
                                                 WeatherTelegramBot weatherTelegramBot) {
        return new ResponseJobCreator(weatherThreadPool, commandMessageAccumulator, failMessageAccumulator) {
            @Override
            protected ResponseJob getCommandResponseJob(UserQueryHolder holder) {
                ResponseJob responseJob = commandResponseJob(telegramCommandInvoker, weatherTelegramBot);
                responseJob.setUserQueryHolder(holder);
                return responseJob;
            }

            @Override
            protected ResponseJob getFailResponseJob(UserQueryHolder holder) {
                ResponseJob responseJob = failResponseJobPrototype(weatherTelegramBot);
                responseJob.setUserQueryHolder(holder);
                return responseJob;
            }
        };
    }

    @Bean
    @Scope("prototype")
    public ResponseJob commandResponseJob(TelegramCommandInvoker telegramCommandInvoker, WeatherTelegramBot weatherTelegramBot) {
        return new CommandResponseJob(telegramCommandInvoker) {
            @Override
            protected WeatherTelegramBot getWeatherBot() {
                return weatherTelegramBot;
            }
        };
    }

    @Bean
    @Scope("prototype")
    public ResponseJob failResponseJobPrototype(WeatherTelegramBot weatherTelegramBot) {
        return new FailResponseJob() {
            @Override
            protected WeatherTelegramBot getWeatherBot() {
                return weatherTelegramBot;
            }
        };
    }
}
