package com.sokima.weather.api.service.grpc;

import com.sokima.weather.api.exception.FetchWeatherException;
import com.sokima.weather.api.service.RealtimeWeatherService;
import com.sokima.weather.api.util.ProtoRequestUtils;
import com.sokima.weather.proto.CityRequest;
import com.sokima.weather.proto.RegionRequest;
import com.sokima.weather.proto.WeatherReport;
import com.sokima.weather.proto.WeatherServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.core.publisher.SignalType;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class GrpcWeatherService extends WeatherServiceGrpc.WeatherServiceImplBase {

    private final RealtimeWeatherService realtimeWeatherService;

    @Override
    public void fetchWeatherForCity(CityRequest request, StreamObserver<WeatherReport> responseObserver) {
        realtimeWeatherService.getWeatherReportForCity(request)
                .doOnNext(responseObserver::onNext)
                .doFinally(signal ->
                        {
                            if (signal == SignalType.ON_COMPLETE) {
                                log.info("Successfully fetched city request : {}", ProtoRequestUtils.toString(request));
                                responseObserver.onCompleted();
                            } else {
                                log.warn("Failed fetching city request : {}", ProtoRequestUtils.toString(request));
                                responseObserver.onError(new FetchWeatherException("Fail to fetch city request."));
                            }
                        }
                )
                .subscribe()
        ;
    }

    @Override
    public StreamObserver<CityRequest> fetchWeatherForCities(StreamObserver<WeatherReport> responseObserver) {
        // TODO fetchWeatherForCities
        throw new UnsupportedOperationException();
    }

    @Override
    public void fetchWeatherForRegion(RegionRequest request, StreamObserver<WeatherReport> responseObserver) {
        realtimeWeatherService.getWeatherReportForMajorCities(request)
                .doOnNext(responseObserver::onNext)
                .then()
                .doFinally(signal ->
                        {
                            if (signal == SignalType.ON_COMPLETE) {
                                log.info("Successfully fetched region request : {}", ProtoRequestUtils.toString(request));
                                responseObserver.onCompleted();
                            } else {
                                log.warn("Failed fetching region request : {}", ProtoRequestUtils.toString(request));
                                responseObserver.onError(new FetchWeatherException("Fail to fetch city request."));
                            }
                        }
                )
                .subscribe()
        ;
    }
}
