package com.sokima.weather.telegram.bot.command.flagable;

import com.sokima.weather.telegram.bot.command.output.AsyncCommandOutput;
import com.sokima.weather.telegram.bot.command.output.CommandOutput;
import com.sokima.weather.telegram.bot.command.output.MultipleStringCommandOutput;
import com.sokima.weather.telegram.bot.command.output.SingleStringCommandOutput;
import com.sokima.weather.telegram.service.WeatherAggregatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocationTelegramCommand extends AbstractFlagableTelegramCommand implements FlagableTelegramCommand {

    private static final List<String> SUPPORTED_FLAGS = List.of("-a");
    private static final String FAILED_PROCESSING_MESSAGE = "Ooops, something went wrong..";

    private final WeatherAggregatorService weatherAggregatorService;

    @Override
    public String command() {
        return "/location";
    }

    @Override
    public CommandOutput<?> execute(String[] params) {
        CommandOutput<?> commandOutput;

        // we are expecting only one params, so gonna process first one.
        String[] split = params[0].split("=");
        String key = split[0];
        String value = split[1];

        log.info("LocationCommand recognized the Param[{}]", key);
        switch (key) {
            case "coordinate" -> {
                String[] splitValue = value.split(",");
                Float[] coordinates = new Float[2];
                coordinates[0] = Float.parseFloat(splitValue[0]);
                coordinates[1] = Float.parseFloat(splitValue[1]);

                String weatherReport = weatherAggregatorService.getWeatherReportForCoordinate(coordinates);
                commandOutput = SingleStringCommandOutput.create(weatherReport);
            }
            case "city" -> {
                String weatherReport = weatherAggregatorService.getWeatherReportForCity(value);
                commandOutput = SingleStringCommandOutput.create(weatherReport);
            }
            case "country" -> {
                switch (flag()) {
                    case "-a" -> {
                        log.info("LocationCommand[{}] run in async mode.", key);
                        weatherAggregatorService.getWeatherReportForCountryAsync(value, context);
                        commandOutput = AsyncCommandOutput.create();
                    }
                    default -> {
                        log.info("LocationCommand[{}] run in normal mode.", key);
                        List<String> weatherReports = weatherAggregatorService.getWeatherReportForCountry(value);
                        commandOutput = MultipleStringCommandOutput.create(weatherReports);
                    }
                }
            }
            default -> {
                log.warn("LocationCommand failed to recognize the Param[{}]", key);
                commandOutput = SingleStringCommandOutput.create(FAILED_PROCESSING_MESSAGE);
            }
        }

        return commandOutput;
    }

    /**
     * Supported flags:
     * -a : run async (applicable for country only)
     */
    @Override
    public List<String> flags() {
        return SUPPORTED_FLAGS;
    }
}
