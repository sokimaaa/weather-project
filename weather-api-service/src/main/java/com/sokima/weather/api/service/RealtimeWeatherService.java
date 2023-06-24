package com.sokima.weather.api.service;

import com.sokima.weather.api.domain.MajorCityResponse;
import com.sokima.weather.api.domain.RealtimeWeatherResponse;
import com.sokima.weather.api.service.client.WeatherWebClient;
import com.sokima.weather.api.util.ProtoRequestUtils;
import com.sokima.weather.proto.CityRequest;
import com.sokima.weather.proto.RegionRequest;
import com.sokima.weather.proto.WeatherReport;
import com.sokima.weather.proto.common.Coordinate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RealtimeWeatherService {

    private final WeatherWebClient weatherWebClient;

    private final GeographyService geographyService;

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

    public Flux<WeatherReport> getWeatherReportForMajorCities(RegionRequest request) {
        Flux<MajorCityResponse> majorCities = geographyService.getMajorCities(request);
        return majorCities
                .map(majorCity -> CityRequest.newBuilder()
                        .setCoordinate(
                                Coordinate.newBuilder()
                                        .setLongitude(majorCity.longitude())
                                        .setLatitude(majorCity.latitude())
                                        .build()
                        )
                        .build()
                )
                .map(this::getWeatherReportForCity)
                .flatMap(mono -> mono);
    }
}
