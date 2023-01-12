package nextstep.etc.console;

import lombok.Getter;

import java.util.List;

public class Command {

    private static final String COMMAND_DELIMITER = " ";
    private static final String PARAMS_DELIMITER = ",";

    @Getter
    private final CommandType type;

    @Getter
    private final Params params;

    public Command(String input) {
        String[] commands = input.split(COMMAND_DELIMITER);
        this.type = CommandType.from(commands[0]);
        this.params = parseParams(commands);
    }

    public Params parseParams(String[] commands) {
        if (commands.length < 2) {
            return new Params();
        }
        String[] params = commands[1].split(PARAMS_DELIMITER);

        return new Params(List.of(params));
    }

    public boolean isQuit() {
        return type.equals(CommandType.QUIT);
    }
}