package com.sokima.weather.api.service;

import com.sokima.weather.api.domain.RealtimeWeatherResponse;
import com.sokima.weather.api.service.client.WeatherWebClient;
import com.sokima.weather.api.util.ProtoRequestUtils;
import com.sokima.weather.proto.CityRequest;
import com.sokima.weather.proto.WeatherReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RealtimeWeatherService {

    private final WeatherWebClient weatherWebClient;

    public Mono<WeatherReport> getWeatherReportForCity(CityRequest request) {
        String query = ProtoRequestUtils.toString(request);
        Mono<RealtimeWeatherResponse> realtimeWeatherData = weatherWebClient.getRealtimeWeatherData(query);
        return realtimeWeatherData.map(
                weatherData -> WeatherReport.newBuilder()
                        .setTemperatureCelsius(weatherData.realtimeWeather().temperatureCelsius())
                        .setFeelsLikeCelsius(weatherData.realtimeWeather().feelsLikeCelsius())
                        .setCondition(weatherData.realtimeWeather().weatherCondition().condition())
                        .setIsDay(weatherData.realtimeWeather().isDay() == 1)
                        .setHumidity(weatherData.realtimeWeather().humidity())
                        .setPressureMb(weatherData.realtimeWeather().pressureMb())
                        .setPrecipitationMm(weatherData.realtimeWeather().precipitationMm())
                        .setWindKph(weatherData.realtimeWeather().windKph())
                        .build()
        );
    }
}
