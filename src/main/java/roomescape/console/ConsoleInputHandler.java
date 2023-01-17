package roomescape.console;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ConsoleInputHandler {
    private final ConsoleView consoleView;

    public ConsoleInputHandler(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public Command getInput() {
        try {
            String input = consoleView.inputCommand();
            Command cmd = Command.valueOf(getCommand(input));
            cmd.setParams(getParams(input));
            return cmd;
        } catch (Exception e) {
            return Command.INVALID_INPUT;
        }
    }

    public String getCommand(String input) {
        String[] splitted = input.split(" ");
        return String.join("_", Arrays.copyOf(splitted, Math.min(splitted.length, 2))).toUpperCase();
    }

    // 이름, 설명 등의 공백은 유지
    public String[] getParams(String input) {
        String[] splitted = input.split(" ");
        String[] subArray = Arrays.copyOfRange(splitted, Math.min(splitted.length, 2), splitted.length);
        return Arrays.stream(String.join(" ", subArray).split(","))
                .map(String::strip)
                .toArray(String[]::new);
    }

    public void close() {
        consoleView.close();
    }

    public void receivedInvalidInput() {
        consoleView.showInvalidInput();
    }
}
