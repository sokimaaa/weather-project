package com.sokima.weather.telegram.service.job;

import com.sokima.weather.telegram.bot.invoker.TelegramCommandInvoker;
import com.sokima.weather.telegram.domain.ActionEnum;
import com.sokima.weather.telegram.domain.UserCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class CommandResponseJob extends AbstractResponseJob implements ResponseJob {

    private final TelegramCommandInvoker commandInvoker;

    @Override
    public void doJob() {
        ActionEnum actionEnum = holder.actionEnum();
        if (actionEnum.equals(ActionEnum.COMMAND)) {
            sendMessage();
            log.info("CommandResponseJob[{}] completed.", Thread.currentThread().getName());
        } else {
            log.warn("CommandResponseJob[{}] completed ahead because of Action[{}].", Thread.currentThread().getName(), actionEnum);
        }
    }

    @Override
    protected String answer() {
        UserCommand userCommand = holder.userCommand();
        return commandInvoker.invokeCommand(userCommand, holder.message());
    }
}
