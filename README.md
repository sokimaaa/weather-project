# weather-project

Spring Boot Project for getting weather report.
Mostly, `weather-project` was designed for sake of 
implementing gRPC in Spring Boot project and get knowledge 
how to work with third-party APIs.

As a result, we got MVP project that ready-to-use. You can easily 
enter your TOKENs, build project and test it out.

Detailed requirements for running each module you can find in its README. 

## Technology Stack
- Java 17
- Spring (Boot, Reactive, gRPC)
- gRPC + Proto
- Java Multithreading

## Modules
- [Weather API Service](./weather-api-service/README.md) 
- [Telegram Service](./telegram-service/README.md)
- [Proto](./proto/README.md)

## Used API

- [Telegram API](https://core.telegram.org/)
- [Weather API](https://www.weatherapi.com)
- [Geography API](https://apilayer.com/marketplace/geo-api)


## Cautions

> Take into account that `AbstractWebClient#logRequestFilter` writes in log a real TOKENs.
> So, you should disable that filter, or encode tokens.
> It was done intentionally.

> Async execution works unstable cause telegram's limit 
> of sent requests. To avoid it, you may limit amount of weather report
> produced by `weather-api-service`.

> Proto module should be built firstly,
> otherwise you got exceptions while building other modules.
