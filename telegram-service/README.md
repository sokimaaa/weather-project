# Telegram Service

The Telegram Service has several responsibility
1) is to interact with gRPC client
2) is to interact with Telegram Api
3) is to handle multiple users via Java Concurrency

## Requirements

- Java 17+
- Apache Maven 3.8.6+
- Set env variables
- Built proto module before
- Run gRPC client before

## Required Environment Variables

- TELEGRAM_BOT_USERNAME : telegram bot username value
- TELEGRAM_BOT_TOKEN : telegram bot token (or api key)

> To get this data you can text to @BotFather (proved by Telegram one).

## Build Project via Maven

```
mvn clean install
```

> You should be in ./telegram-service directory previously.
