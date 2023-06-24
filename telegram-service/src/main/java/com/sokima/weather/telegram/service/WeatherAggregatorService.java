package com.sokima.weather.telegram.service;

import com.sokima.weather.proto.CityRequest;
import com.sokima.weather.proto.RegionRequest;
import com.sokima.weather.proto.WeatherReport;
import com.sokima.weather.proto.WeatherServiceGrpc;
import com.sokima.weather.proto.common.Coordinate;
import com.sokima.weather.telegram.bot.command.output.SingleStringCommandOutput;
import com.sokima.weather.telegram.domain.UserQueryHolder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class WeatherAggregatorService {
    @GrpcClient("weather-service")
    private WeatherServiceGrpc.WeatherServiceBlockingStub weatherBlockingStub;

    @GrpcClient("weather-service")
    private WeatherServiceGrpc.WeatherServiceStub weatherStub;

    @Autowired
    private MessageSenderService messageSenderService;

    @Autowired
    private TableWeatherReportService tableService;

    public String getWeatherReportForCity(String city) {
        CityRequest build = CityRequest.newBuilder()
                .setName(city)
                .build();
        WeatherReport weatherReport = weatherBlockingStub.fetchWeatherForCity(build);
        return tableService.makeTable(weatherReport);
    }

    public String getWeatherReportForCoordinate(Float[] coordinate) {
        CityRequest build = CityRequest.newBuilder().setCoordinate(
                Coordinate.newBuilder()
                        .setLatitude(coordinate[0])
                        .setLongitude(coordinate[1])
                        .build()
        ).build();
        WeatherReport weatherReport = weatherBlockingStub.fetchWeatherForCity(build);
        return tableService.makeTable(weatherReport);
    }

    public List<String> getWeatherReportForCountry(String countryCode) {
        RegionRequest request = RegionRequest.newBuilder().setCountry(countryCode).build();
        Iterator<WeatherReport> weatherReports = weatherBlockingStub.fetchWeatherForRegion(request);
        List<String> weatherReportTables = new ArrayList<>();
        while (weatherReports.hasNext()) {
            WeatherReport report = weatherReports.next();
            String table = tableService.makeTable(report);
            weatherReportTables.add(table);
        }

        return weatherReportTables;
    }

    public void getWeatherReportForCountryAsync(String countryCode, UserQueryHolder holder) {
        RegionRequest request = RegionRequest.newBuilder().setCountry(countryCode).build();
        weatherStub.fetchWeatherForRegion(request, new StreamObserver<>() {
            @Override
            public void onNext(WeatherReport weatherReport) {
                try {
                    final String table = tableService.makeTable(weatherReport);
                    SingleStringCommandOutput singleStringCommandOutput = SingleStringCommandOutput.create(table);
                    messageSenderService.sendMessage(holder, singleStringCommandOutput);
                } catch (Exception ex) {
                    log.error("Got exception while processing weather report : {}", ex.getMessage());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                final String completedMessage = "Async execution interrupted :(";
                SingleStringCommandOutput singleStringCommandOutput = SingleStringCommandOutput.create(completedMessage);
                messageSenderService.sendMessage(holder, singleStringCommandOutput);
                log.warn("Got an error while async weather report retrieving : {}", throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                final String completedMessage = "Async execution completed :)";
                SingleStringCommandOutput singleStringCommandOutput = SingleStringCommandOutput.create(completedMessage);
                messageSenderService.sendMessage(holder, singleStringCommandOutput);
                log.info("Async weather report retrieving has completed.");
            }
        });
    }
}
