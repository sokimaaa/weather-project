package com.sokima.weather.api.util;

import com.sokima.weather.proto.CityRequest;
import com.sokima.weather.proto.common.Coordinate;

import java.util.Locale;

public final class ProtoRequestUtils {

    private ProtoRequestUtils() {

    }

    public static String toString(CityRequest request) {
        Coordinate coordinate = request.getCoordinate();
        String name = request.getName();
        if (request.hasName()) {
            return name;
        }

        if (request.hasCoordinate()) {
            return toString(coordinate);
        }
        throw new IllegalStateException();
    }

    public static String toString(Coordinate coordinate) {
        return String.format(Locale.US, "%.2f,%.2f", coordinate.getLatitude(), coordinate.getLongitude());
    }
}
