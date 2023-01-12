package nextstep.etc.console;

import lombok.Getter;

import java.util.Arrays;

public enum CommandType {

    NONE("none", 0),
    ADD("add", 3),
    FIND("find", 1),
    DELETE("delete", 1),
    QUIT("quit", 0);

    private final String command;

    @Getter
    private final int paramCount;

    CommandType(String command, int paramCount) {
        this.command = command;
        this.paramCount = paramCount;
    }

    public static CommandType from(String command) {
        return Arrays.stream(values())
                .filter(m -> m.isMatch(command))
                .findAny()
                .orElse(NONE);
    }

    private boolean isMatch(String input) {
        return input.equals(command);
    }
}
