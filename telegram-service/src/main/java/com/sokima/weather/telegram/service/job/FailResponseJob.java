package com.sokima.weather.telegram.service.job;

import com.sokima.weather.telegram.bot.command.output.SingleStringCommandOutput;
import com.sokima.weather.telegram.service.MessageSenderService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FailResponseJob extends AbstractResponseJob implements ResponseJob {

    public FailResponseJob(MessageSenderService messageSenderService) {
        super(messageSenderService);
    }

    @Override
    public void doJob() {
        final String failMessage = """
                For some reasons your request wasn't processed.
                Please, verify your command.
                                
                If you're sure about command, possibly that we have high-load now :(
                Try to send request again or later.
                """;
        SingleStringCommandOutput commandOutput = SingleStringCommandOutput.create(failMessage);
        messageSenderService.sendMessage(holder, commandOutput);
        log.info("FailResponseJob[{}] completed.", Thread.currentThread().getName());
    }
}
