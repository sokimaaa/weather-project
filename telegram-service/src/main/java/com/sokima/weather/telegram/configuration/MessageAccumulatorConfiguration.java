package com.sokima.weather.telegram.configuration;

import com.sokima.weather.telegram.annotation.MessageQueue;
import com.sokima.weather.telegram.domain.UserQueryHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class MessageAccumulatorConfiguration {

    @Value("${weather.message-accumulator.bound}")
    private int bound;

    @Bean
    @MessageQueue
    @Scope("prototype")
    public BlockingQueue<UserQueryHolder> blockingQueue() {
        // bound needed to avoid OutOfMemory
        // in future should be exchanged to Message Brokers, e.g. Apache Kafka
        return new LinkedBlockingQueue<>(bound);
    }
}
