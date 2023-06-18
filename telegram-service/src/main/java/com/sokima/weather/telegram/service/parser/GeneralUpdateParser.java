package com.sokima.weather.telegram.service.parser;

import com.sokima.weather.telegram.domain.ActionEnum;
import com.sokima.weather.telegram.domain.UserCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@Component
public class GeneralUpdateParser extends AbstractUpdateParser {

    /**
     * Key - supported keys for params
     * Value - regex to validate param's value
     */
    private static final Map<String, Pattern> PARAM = Map.of(
            "coordinate", Pattern.compile("^([+-]?\\d+(?:\\.\\d+)?),([+-]?\\d+(?:\\.\\d+)?)$"),
            "city", Pattern.compile("^([A-Za-z ]+)$")
    );

    @Value("${weather.command-parser.split.regex}")
    private String splitRegex;

    @Value("${weather.command-parser.start-symbol.command}")
    private String commandSymbol;

    @Value("${weather.command-parser.start-symbol.flag}")
    private String flagSymbol;

    @Autowired
    @Qualifier("commands")
    private List<String> commands;

    @Override
    protected UserCommand parseCommand(String text) {
        String[] split = text.split(splitRegex);
        String command = split[0].startsWith(commandSymbol) ? split[0] : null;
        String[] flags = getFlags(split);
        String[] params = getParams(split);

        UserCommand userCommand = UserCommand.create(command, flags, params);
        log.info("Text[{}] was parsed into Command[{}]", text, userCommand);
        return userCommand;
    }

    private String[] getFlags(String[] split) {
        List<String> result = new ArrayList<>();

        for (String flag : split) {
            if (flag.startsWith(flagSymbol)) {
                result.add(flag);
            }
        }

        return result.toArray(new String[0]);
    }

    private String[] getParams(String[] split) {
        List<String> result = new ArrayList<>();

        for (String param : split) {
            if (!param.startsWith(commandSymbol) && !param.startsWith(flagSymbol) && param.contains("=")) {
                result.add(param);
            }
        }

        return result.toArray(new String[0]);
    }

    @Override
    protected ActionEnum parseAction(UserCommand userCommand) {
        String command = userCommand.command();

        if (Objects.isNull(command)) {
            return ActionEnum.IGNORE;
        }
        final boolean isCommand = commands.contains(command);

        if (!isCommand) {
            return ActionEnum.UNRECOGNIZED_COMMAND;
        }

        String[] params = userCommand.params();
        final boolean hasParams = userCommand.hasParams();
        final boolean isParamsValid = validateParams(params);

        String[] flags = userCommand.flags();
        final boolean hasFlags = userCommand.hasFlags();
        final boolean isFlagsValid = validateFlags(flags);

        if ((hasParams && !isParamsValid) || (hasFlags && !isFlagsValid)) {
            return ActionEnum.WRONG_COMMAND;
        }

        return ActionEnum.COMMAND;
    }

    private boolean validateFlags(String[] flags) {
        Pattern pattern = Pattern.compile("^-[a-zA-Z]$");
        for (String flag : flags) {
            if (!pattern.matcher(flag).matches()) {
                return false;
            }
        }

        return true;
    }

    private boolean validateParams(String[] params) {
        if (params.length == 0) {
            return false;
        }

        for (String param : params) {
            if (!param.contains("=")) {
                return false;
            }
            String[] split = param.split("=");
            Pattern pattern = PARAM.get(split[0]);
            String value = split[1];

            if (Objects.isNull(pattern)) {
                return false;
            }

            if (!pattern.matcher(value).matches()) {
                return false;
            }
        }

        return true;
    }
}
