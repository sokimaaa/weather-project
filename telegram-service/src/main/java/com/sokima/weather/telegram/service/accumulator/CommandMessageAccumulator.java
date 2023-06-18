package com.sokima.weather.telegram.service.accumulator;

import com.sokima.weather.telegram.annotation.MessageQueue;
import com.sokima.weather.telegram.domain.UserQueryHolder;
import com.sokima.weather.telegram.exception.MessageAccumulatingException;
import com.sokima.weather.telegram.exception.MessageAcquiringException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Primary
public class CommandMessageAccumulator implements MessageAccumulator {

    @Value("${weather.message-accumulator.acquire.timeout}")
    private int acquireTimeout;

    @Value("${weather.message-accumulator.accumulate.timeout}")
    private int accumulateTimeout;

    @Autowired
    @MessageQueue
    private BlockingQueue<UserQueryHolder> holderQueue;

    @Autowired
    @Qualifier("failMessageAccumulator")
    private MessageAccumulator failMessageAccumulator;

    @Override
    public UserQueryHolder acquire() {
        try {
            UserQueryHolder holder = holderQueue.poll(acquireTimeout, TimeUnit.MILLISECONDS);

            if (Objects.isNull(holder)) {
                log.warn("HolderQueueLength[{}] fail to acquire message.", holderQueue.size());
                throw new MessageAcquiringException("Fail to acquire message.");
            }
            log.info("User[{}] : Message[{}] was acquired.", holder.user().getId(), holder.message().getMessageId());
            return holder;
        } catch (InterruptedException e) {
            log.warn("{} thread was interrupted.", Thread.currentThread().getName());
            Thread.currentThread().interrupt();
            throw new MessageAcquiringException("Thread was interrupted while publishing.");
        }
    }

    @Override
    public boolean hasMessage() {
        return !holderQueue.isEmpty();
    }

    @Override
    public void accumulate(UserQueryHolder holder) {
        try {
            final boolean offer = holderQueue.offer(holder, accumulateTimeout, TimeUnit.MILLISECONDS);

            if (!offer) {
                log.warn("User[{}] : Message[{}] was not published.", holder.user().getId(), holder.message().getMessageId());
                failMessageAccumulator.accumulate(holder);
                throw new MessageAccumulatingException("Fail to add message to HolderQueue.");
            }
        } catch (InterruptedException ex) {
            log.warn("{} thread was interrupted.", Thread.currentThread().getName());
            failMessageAccumulator.accumulate(holder);
            Thread.currentThread().interrupt();
            throw new MessageAccumulatingException("Thread was interrupted while publishing.");
        }
    }
}
