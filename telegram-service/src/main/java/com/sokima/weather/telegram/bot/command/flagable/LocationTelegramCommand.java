package com.sokima.weather.telegram.bot.command.flagable;

import com.sokima.weather.telegram.service.WeatherAggregatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocationTelegramCommand extends AbstractFlagableTelegramCommand implements FlagableTelegramCommand {

    private static final List<String> SUPPORTED_FLAGS = List.of("-s", "-f");

    private final WeatherAggregatorService weatherAggregatorService;

    @Override
    public String command() {
        return "/location";
    }

    @Override
    public String execute(String[] params) {
        for (String param : params) {
            String[] split = param.split("=");
            String key = split[0];
            String value = split[1];

            switch (key) {
                case "coordinate" -> {
                    String[] splitValue = value.split(",");
                    Float[] coordinates = new Float[2];
                    coordinates[0] = Float.parseFloat(splitValue[0]);
                    coordinates[1] = Float.parseFloat(splitValue[1]);

                    log.info("LocationCommand recognized the Param[{}]", key);
                    return weatherAggregatorService.getWeatherReportForCoordinate(coordinates);
                }
                case "city" -> {
                    log.info("LocationCommand recognized the Param[{}]", key);
                    return weatherAggregatorService.getWeatherReportForCity(value);
                }
                default -> log.warn("LocationCommand failed to recognize the Param[{}]", key);
            }
        }
        return "Ooops, something went wrong..";
    }

    /**
     * TODO: location flags not yet supported
     * Supported flags:
     * -s : start stream params
     * -e : end stream params
     */
    @Override
    public List<String> flags() {
        return SUPPORTED_FLAGS;
    }
}
