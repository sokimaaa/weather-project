package com.sokima.weather.telegram.service;

import com.sokima.weather.telegram.bot.WeatherTelegramBot;
import com.sokima.weather.telegram.bot.command.output.AsyncCommandOutput;
import com.sokima.weather.telegram.bot.command.output.CommandOutput;
import com.sokima.weather.telegram.bot.command.output.MultipleCommandOutput;
import com.sokima.weather.telegram.domain.UserQueryHolder;
import com.sokima.weather.telegram.exception.MessageSendingException;
import com.sokima.weather.telegram.service.accumulator.FailMessageAccumulator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSenderService {

    private static final int RETRY_NUMBER = 3;

    private final WeatherTelegramBot weatherTelegramBot;

    private final FailMessageAccumulator failMessageAccumulator;

    public void sendMessage(UserQueryHolder holder, CommandOutput<?> commandOutput) {
        if (commandOutput.isThrowableOutput()) {
            log.warn("Got a throwable command output.");
            commandOutput.getResult();
        }

        if (commandOutput.isAsyncOutput()) {
            log.info("Got an async command output.");
            Instant startTime = ((AsyncCommandOutput) commandOutput).getResult();
            log.info("Async execution started : {}", startTime);
        }

        if (commandOutput.isMultipleOutput()) {
            log.info("Got a multiple command output.");
            MultipleCommandOutput<?> multipleAnswer = (MultipleCommandOutput<?>) commandOutput;
            Iterable<?> iterable = multipleAnswer.getResult();
            for (Object obj : iterable) {
                constructAndSendMessage(holder, obj);
            }
        }

        if (commandOutput.isSingleOutput()) {
            log.info("Got a single command output.");
            Object singleAnswer = commandOutput.getResult();
            constructAndSendMessage(holder, singleAnswer);
        }
    }

    private void constructAndSendMessage(UserQueryHolder holder, Object obj) {
        SendMessage message = constructMessage(holder, obj);

        int retry = 0;
        executeMessage(retry, holder, message);
    }

    private SendMessage constructMessage(UserQueryHolder holder, Object obj) {
        SendMessage message = new SendMessage();
        message.setChatId(holder.chatId());
        message.setText(obj.toString());
        message.setParseMode(ParseMode.HTML);

        return message;
    }

    private void executeMessage(int retry, UserQueryHolder holder, SendMessage message) {
        try {
            weatherTelegramBot.execute(message);
            log.info("User[{}] : Message[{}] was successfully delivered.", holder.user().getId(), holder.message().getMessageId());
        } catch (TelegramApiException e) {
            log.warn("Retry[-{}-] : Fail to send message to the user.", retry + 1);
            if (retry < RETRY_NUMBER) {
                executeMessage(++retry, holder, message);
            }
            log.error("Fail to send message to user : {}", e.getMessage());
            throw new MessageSendingException("Fail to send message to user.");
        }
    }
}
