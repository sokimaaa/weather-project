package com.sokima.weather.api.util;

import com.sokima.weather.proto.CityRequest;
import com.sokima.weather.proto.common.Coordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProtoRequestUtilsTest {

    @Test
    void testToStringCityRequest() {
        String expectedName = "City";
        Coordinate coordinate = Coordinate.newBuilder()
                .setLatitude(5.01F)
                .setLongitude(20.05F)
                .build();
        String expectedCoordinate = ProtoRequestUtils.toString(coordinate);

        CityRequest onlyNameCityRequest = CityRequest.newBuilder()
                .setName(expectedName)
                .build();

        CityRequest onlyCoordinateCityRequest = CityRequest.newBuilder()
                .setCoordinate(coordinate)
                .build();

        CityRequest emptyCityRequest = CityRequest.newBuilder()
                .build();

        String actualNameCityRequest = ProtoRequestUtils.toString(onlyNameCityRequest);
        Assertions.assertEquals(expectedName, actualNameCityRequest);

        String actualCoordinateCityRequest = ProtoRequestUtils.toString(onlyCoordinateCityRequest);
        Assertions.assertEquals(expectedCoordinate, actualCoordinateCityRequest);

        Assertions.assertThrows(IllegalStateException.class, () -> ProtoRequestUtils.toString(emptyCityRequest));
    }

    @Test
    void testToStringCoordinate() {
        Coordinate coordinate = Coordinate
                .newBuilder()
                .setLatitude(10.00F)
                .setLongitude(25.006F)
                .build();

        final String actualCoordinate = ProtoRequestUtils.toString(coordinate);
        final String expectedCoordinate = "10.00,25.01";

        Assertions.assertNotNull(actualCoordinate);
        Assertions.assertFalse(actualCoordinate.isBlank());
        Assertions.assertEquals(expectedCoordinate, actualCoordinate);

        Coordinate emptyCoordinate = Coordinate
                .newBuilder()
                .build();

        final String actualEmptyCoordinate = ProtoRequestUtils.toString(emptyCoordinate);
        final String expectedEmptyCoordinate = "0.00,0.00";

        Assertions.assertNotNull(actualEmptyCoordinate);
        Assertions.assertFalse(actualEmptyCoordinate.isBlank());
        Assertions.assertEquals(expectedEmptyCoordinate, actualEmptyCoordinate);
    }
}
