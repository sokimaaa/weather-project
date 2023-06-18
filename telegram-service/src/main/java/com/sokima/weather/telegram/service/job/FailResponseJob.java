package com.sokima.weather.telegram.service.job;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class FailResponseJob extends AbstractResponseJob implements ResponseJob {

    @Override
    public void doJob() {
        sendMessage();
        log.info("FailResponseJob[{}] completed.", Thread.currentThread().getName());
    }

    @Override
    protected String answer() {
        return new StringBuilder()
                .append("For some reasons your request wasn't processed.\n")
                .append("Please, verify your command.\n\n")
                .append("If you're sure about command, possibly that we have high-load now :(\n")
                .append("Try to send request again or later.")
                .toString();
    }
}
