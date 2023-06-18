package com.sokima.weather.telegram.service.job;

import com.sokima.weather.telegram.domain.UserQueryHolder;

public interface ResponseJob extends Runnable {

    /**
     * executes logic and send message to user.
     */
    void doJob();

    void setUserQueryHolder(UserQueryHolder holder);

    default void run() {
        doJob();
    }
}
