package com.sokima.weather.telegram.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherThreadPool {

    @Value("${weather.thread-pool.\"command\".number-threads}")
    private int commandPoolNumberThreads;

    @Value("${weather.thread-pool.\"fail\".number-threads}")
    private int failPoolNumberThreads;

    @Getter
    private ThreadPoolExecutor commandResponseJobPool;

    @Getter
    private ThreadPoolExecutor failResponseJobPool;

    @PostConstruct
    public void initExecutors() {
        commandResponseJobPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(commandPoolNumberThreads, new ThreadFactoryBuilder()
                .setNameFormat("command-job-%d")
                .setThreadFactory(Executors.defaultThreadFactory())
                .build());

        failResponseJobPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(failPoolNumberThreads, new ThreadFactoryBuilder()
                .setNameFormat("fail-job-%d")
                .setThreadFactory(Executors.defaultThreadFactory())
                .build());
    }

    @Scheduled(cron = "${weather.thread-pool.log-cron}")
    public void showThreadPoolsState() {
        log.warn("Does not support yet | showThreadPoolsState");
        // TODO: log each thread pool
        // TODO: make it repeated called
    }
}
