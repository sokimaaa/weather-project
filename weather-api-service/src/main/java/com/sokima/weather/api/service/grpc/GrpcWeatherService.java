package com.sokima.weather.api.service.grpc;

import com.sokima.weather.api.service.RealtimeWeatherService;
import com.sokima.weather.proto.CityRequest;
import com.sokima.weather.proto.RegionRequest;
import com.sokima.weather.proto.WeatherReport;
import com.sokima.weather.proto.WeatherServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class GrpcWeatherService extends WeatherServiceGrpc.WeatherServiceImplBase {

    private final RealtimeWeatherService realtimeWeatherService;

    @Override
    public void fetchWeatherForCity(CityRequest request, StreamObserver<WeatherReport> responseObserver) {
        realtimeWeatherService.getWeatherReportForCity(request)
                .doOnNext(responseObserver::onNext)
                .subscribe(weatherReport -> responseObserver.onCompleted())
        ;
    }

    @Override
    public StreamObserver<CityRequest> fetchWeatherForCities(StreamObserver<WeatherReport> responseObserver) {
        // TODO fetchWeatherForCities
        throw new UnsupportedOperationException();
    }

    @Override
    public void fetchWeatherForRegion(RegionRequest request, StreamObserver<WeatherReport> responseObserver) {
        // TODO fetchWeatherForRegion
        throw new UnsupportedOperationException();
    }
}
