package com.sokima.weather.telegram.service.job;

import com.sokima.weather.telegram.domain.UserQueryHolder;
import com.sokima.weather.telegram.service.MessageSenderService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractResponseJob implements ResponseJob {

    protected final MessageSenderService messageSenderService;

    protected UserQueryHolder holder;

    protected AbstractResponseJob(MessageSenderService messageSenderService) {
        this.messageSenderService = messageSenderService;
    }

    @Override
    public void setUserQueryHolder(UserQueryHolder holder) {
        this.holder = holder;
    }
}
