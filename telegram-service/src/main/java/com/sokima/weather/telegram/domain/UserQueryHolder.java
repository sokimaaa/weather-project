package com.sokima.weather.telegram.domain;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

public record UserQueryHolder(
        User user,
        Long chatId,
        Message message,
        UserCommand userCommand,
        ActionEnum actionEnum
) {
    public static UserQueryHolder create(User user, Long chatId, Message message, UserCommand userCommand, ActionEnum actionEnum) {
        return new UserQueryHolder(user, chatId, message, userCommand, actionEnum);
    }

    public static UserQueryHolder create(User user, Message message, UserCommand userCommand, ActionEnum actionEnum) {
        Long chatId = message.getChatId();
        return create(user, chatId, message, userCommand, actionEnum);
    }

    public static UserQueryHolder create(Message message, UserCommand userCommand, ActionEnum actionEnum) {
        User user = message.getFrom();
        return create(user, message, userCommand, actionEnum);
    }

    public static UserQueryHolder create(Message message) {
        return create(message, UserCommand.empty(), ActionEnum.IGNORE);
    }
}
