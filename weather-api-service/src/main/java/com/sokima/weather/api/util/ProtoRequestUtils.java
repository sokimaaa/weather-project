package com.sokima.weather.api.util;

import com.sokima.weather.proto.CityRequest;
import com.sokima.weather.proto.RegionRequest;
import com.sokima.weather.proto.common.Coordinate;

import java.util.Locale;

public final class ProtoRequestUtils {

    private ProtoRequestUtils() {

    }

    public static String toString(RegionRequest request) {
        if (request.hasRegion()) {
            return request.getRegion();
        }

        if (request.hasCountry()) {
            return request.getCountry();
        }

        throw new IllegalStateException("Region request should have at least one param.");
    }

    public static String toString(CityRequest request) {
        if (request.hasName()) {
            return request.getName();
        }

        if (request.hasCoordinate()) {
            Coordinate coordinate = request.getCoordinate();
            return toString(coordinate);
        }

        throw new IllegalStateException();
    }

    public static String toString(Coordinate coordinate) {
        return String.format(Locale.US, "%.2f,%.2f", coordinate.getLatitude(), coordinate.getLongitude());
    }
}
