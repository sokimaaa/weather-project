# Weather Api Service

The Weather Api Service has several responsibility
1) is to interact with third-party APIs (weather api, geo api)
2) is to be gRPC client

## Requirements

- Java 17+
- Apache Maven 3.8.6+
- Set env variables
- Built proto module before

## Required Environment Variables

- WEATHER_TOKEN : token for getting access to weather api
- GEOGRAPHY_TOKEN : token for getting access to geo api

## Build Project via Maven

```
mvn clean install
```

> You should be in ./weather-api-service directory previously.

## Hints

You can use `Postman` for testing gRPC calls. It's pretty comfort to do.

Or, instead you can use `grpcurl`.
