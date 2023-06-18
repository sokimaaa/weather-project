package com.sokima.weather.telegram.service.accumulator;

import com.sokima.weather.telegram.domain.UserQueryHolder;

public interface MessageAccumulator {

    void accumulate(UserQueryHolder holder);

    UserQueryHolder acquire();

    boolean hasMessage();
}
