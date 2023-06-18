package com.sokima.weather.telegram.service;

import com.sokima.weather.proto.CityRequest;
import com.sokima.weather.proto.WeatherReport;
import com.sokima.weather.proto.WeatherServiceGrpc;
import com.sokima.weather.proto.common.Coordinate;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WeatherAggregatorService {
    @GrpcClient("weather-service")
    private WeatherServiceGrpc.WeatherServiceBlockingStub weatherStub;

    @Autowired
    private TableWeatherReportService tableService;

    public String getWeatherReportForCity(String city) {
        CityRequest build = CityRequest.newBuilder()
                .setName(city)
                .build();
        WeatherReport weatherReport = weatherStub.fetchWeatherForCity(build);
        return tableService.makeTable(weatherReport);
    }

    public String getWeatherReportForCoordinate(Float[] coordinate) {
        CityRequest build = CityRequest.newBuilder().setCoordinate(
                Coordinate.newBuilder()
                        .setLatitude(coordinate[0])
                        .setLongitude(coordinate[1])
                        .build()
        ).build();
        WeatherReport weatherReport = weatherStub.fetchWeatherForCity(build);
        return tableService.makeTable(weatherReport);
    }
}
