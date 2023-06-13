package com.sokima.weather.api.domain;

import java.util.regex.Pattern;

public enum WeatherQueryType {

    COORDINATE,
    CITY_NAME,
    US_ZIP,
    UK_POSTCODE,
    CANADA_POSTAL_CODE,
    METAR,
    IATA,
    AUTO_IP,
    IP_ADDRESS,

    UNRECOGNIZED;

    private static final Pattern LAT_AND_LON_REGEX_PATTERN = Pattern.compile("^(-?\\d{1,2}(?:\\.\\d+)?),(-?\\d{1,3}(?:\\.\\d+)?)$");
    private static final Pattern CITY_NAME_REGEX_PATTERN = Pattern.compile("^([A-Za-z ]+)$");
    private static final Pattern US_ZIP_REGEX_PATTERN = Pattern.compile("^(\\d{5})$");
    private static final Pattern UK_POSTCODE_REGEX_PATTERN = Pattern.compile("^([A-Za-z]{1,2}\\d[A-Za-z\\d]? \\d[A-Za-z]{2})$");
    private static final Pattern CANADA_POSTAL_CODE_REGEX_PATTERN = Pattern.compile("^([A-Za-z]\\d[A-Za-z] \\d[A-Za-z]\\d)$");
    private static final Pattern METAR_REGEX_PATTERN = Pattern.compile("^metar:([A-Z]{4})$");
    private static final Pattern IATA_REGEX_PATTERN = Pattern.compile("^iata:([A-Z]{3})$");
    private static final Pattern AUTO_IP_REGEX_PATTERN = Pattern.compile("^auto:ip$");
    private static final Pattern IP_ADDRESS_REGEX_PATTERN = Pattern.compile("^([\\d.:]+)$");

    public static WeatherQueryType getQueryType(String queryToIdentify) {
        if (LAT_AND_LON_REGEX_PATTERN.matcher(queryToIdentify).matches()) {
            return WeatherQueryType.COORDINATE;
        }

        if (CITY_NAME_REGEX_PATTERN.matcher(queryToIdentify).matches()) {
            return WeatherQueryType.CITY_NAME;
        }

        if (US_ZIP_REGEX_PATTERN.matcher(queryToIdentify).matches()) {
            return WeatherQueryType.US_ZIP;
        }

        if (UK_POSTCODE_REGEX_PATTERN.matcher(queryToIdentify).matches()) {
            return WeatherQueryType.UK_POSTCODE;
        }

        if (CANADA_POSTAL_CODE_REGEX_PATTERN.matcher(queryToIdentify).matches()) {
            return WeatherQueryType.CANADA_POSTAL_CODE;
        }

        if (METAR_REGEX_PATTERN.matcher(queryToIdentify).matches()) {
            return WeatherQueryType.METAR;
        }

        if (IATA_REGEX_PATTERN.matcher(queryToIdentify).matches()) {
            return WeatherQueryType.IATA;
        }

        if (AUTO_IP_REGEX_PATTERN.matcher(queryToIdentify).matches()) {
            return WeatherQueryType.AUTO_IP;
        }

        if (IP_ADDRESS_REGEX_PATTERN.matcher(queryToIdentify).matches()) {
            return WeatherQueryType.IP_ADDRESS;
        }

        return WeatherQueryType.UNRECOGNIZED;
    }
}
