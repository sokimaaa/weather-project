package com.sokima.weather.telegram.service.job.creator;

import com.sokima.weather.telegram.domain.UserQueryHolder;
import com.sokima.weather.telegram.service.WeatherThreadPool;
import com.sokima.weather.telegram.service.accumulator.CommandMessageAccumulator;
import com.sokima.weather.telegram.service.accumulator.FailMessageAccumulator;
import com.sokima.weather.telegram.service.job.ResponseJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
public abstract class ResponseJobCreator {

    private final WeatherThreadPool threadPool;
    private final CommandMessageAccumulator commandMessageAccumulator;

    private final FailMessageAccumulator failMessageAccumulator;

    @Scheduled(cron = "${weather.response-job.\"command\".repeat-cron}")
    public void runCommandResponseJob() {
        while (commandMessageAccumulator.hasMessage()) {
            UserQueryHolder holder = commandMessageAccumulator.acquire();
            threadPool.getCommandResponseJobPool().submit(getCommandResponseJob(holder));
            log.info("User[{}] : Message[{}] Command Response Job was submitted.", holder.user().getId(), holder.message().getMessageId());
        }
        log.info("CommandMessageAccumulator was clean up.");
    }

    @Scheduled(cron = "${weather.response-job.\"fail\".repeat-cron}")
    public void runFailResponseJob() {
        while (failMessageAccumulator.hasMessage()) {
            UserQueryHolder holder = failMessageAccumulator.acquire();
            threadPool.getFailResponseJobPool().submit(getFailResponseJob(holder));
            log.info("User[{}] : Message[{}] Fail Response Job was submitted.", holder.user().getId(), holder.message().getMessageId());
        }
        log.info("FailMessageAccumulator was clean up.");
    }

    protected abstract ResponseJob getCommandResponseJob(UserQueryHolder holder);

    protected abstract ResponseJob getFailResponseJob(UserQueryHolder holder);
}
