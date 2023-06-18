package com.sokima.weather.telegram.service.job;

import com.sokima.weather.telegram.bot.WeatherTelegramBot;
import com.sokima.weather.telegram.domain.UserQueryHolder;
import com.sokima.weather.telegram.exception.MessageSendingException;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public abstract class AbstractResponseJob implements ResponseJob {

    private static final int RETRY_NUMBER = 3;

    protected UserQueryHolder holder;

    protected void sendMessage() {
        SendMessage message = new SendMessage();
        message.setChatId(holder.chatId());
        message.setText(answer());
        message.setParseMode(ParseMode.HTML);

        int retry = 0;
        sendMessage(retry, message);
        log.info("User[{}] : Message[{}] was successfully delivered.", holder.user().getId(), holder.message().getMessageId());
    }

    private void sendMessage(int retry, SendMessage message) {
        try {
            WeatherTelegramBot weatherBot = getWeatherBot();
            weatherBot.execute(message);
        } catch (TelegramApiException e) {
            log.warn("Retry[-{}-] : Fail to send message to the user.", retry);
            if (retry < RETRY_NUMBER) {
                sendMessage(++retry, message);
            }
            throw new MessageSendingException("Fail to send message to user.");
        }
    }

    protected abstract WeatherTelegramBot getWeatherBot();

    protected abstract String answer();

    @Override
    public void setUserQueryHolder(UserQueryHolder holder) {
        this.holder = holder;
    }
}
