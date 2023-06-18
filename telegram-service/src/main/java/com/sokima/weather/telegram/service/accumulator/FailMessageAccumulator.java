package com.sokima.weather.telegram.service.accumulator;

import com.sokima.weather.telegram.annotation.MessageQueue;
import com.sokima.weather.telegram.domain.UserQueryHolder;
import com.sokima.weather.telegram.exception.MessageAccumulatingException;
import com.sokima.weather.telegram.exception.MessageAcquiringException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Slf4j
@Component
@Qualifier("failMessageAccumulator")
public class FailMessageAccumulator implements MessageAccumulator {

    @Autowired
    @MessageQueue
    private BlockingQueue<UserQueryHolder> failHolderQueue;

    @Override
    public void accumulate(UserQueryHolder holder) {
        try {
            failHolderQueue.put(holder);
            log.info("User[{}] : Message[{}] was accumulated to FailHolderQueue.", holder.user().getId(), holder.message().getMessageId());
        } catch (InterruptedException ex) {
            log.error("{} thread was interrupted.", Thread.currentThread().getName());
            log.error("FailHolderQueue was interrupted. User[{}] : Message [{}] was gone.", holder.user().getId(), holder.message().getMessageId());
            Thread.currentThread().interrupt();
            throw new MessageAccumulatingException("Thread was interrupted while publishing.");
        }
    }

    @Override
    public UserQueryHolder acquire() {
        try {
            UserQueryHolder holder = failHolderQueue.take();

            log.info("User[{}] : Message[{}] was acquired from FailHolderQueue.", holder.user().getId(), holder.message().getMessageId());
            return holder;
        } catch (InterruptedException e) {
            log.error("{} thread was interrupted.", Thread.currentThread().getName());
            log.error("FailHolderQueue was interrupted. Acquire action was failed.");
            Thread.currentThread().interrupt();
            throw new MessageAcquiringException("Thread was interrupted while publishing.");
        }
    }

    @Override
    public boolean hasMessage() {
        return !failHolderQueue.isEmpty();
    }
}
