package com.sokima.weather.telegram.service.job;

import com.sokima.weather.telegram.bot.command.output.CommandOutput;
import com.sokima.weather.telegram.bot.invoker.TelegramCommandInvoker;
import com.sokima.weather.telegram.domain.ActionEnum;
import com.sokima.weather.telegram.domain.UserCommand;
import com.sokima.weather.telegram.service.MessageSenderService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandResponseJob extends AbstractResponseJob implements ResponseJob {

    private final TelegramCommandInvoker commandInvoker;

    public CommandResponseJob(MessageSenderService messageSenderService, TelegramCommandInvoker commandInvoker) {
        super(messageSenderService);
        this.commandInvoker = commandInvoker;
    }

    @Override
    public void doJob() {
        ActionEnum actionEnum = holder.actionEnum();
        if (actionEnum.equals(ActionEnum.COMMAND)) {
            UserCommand userCommand = holder.userCommand();
            CommandOutput<?> commandOutput = commandInvoker.invokeCommand(userCommand, holder);
            messageSenderService.sendMessage(holder, commandOutput);
            log.info("CommandResponseJob[{}] completed.", Thread.currentThread().getName());
        } else {
            log.warn("CommandResponseJob[{}] completed ahead because of Action[{}].", Thread.currentThread().getName(), actionEnum);
        }
    }
}
