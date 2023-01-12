package nextstep.etc.console;

import java.io.InputStream;
import java.util.Scanner;

public class RoomEscapeInput {

    private final Scanner scanner;

    public RoomEscapeInput(InputStream in) {
        scanner = new Scanner(in);
    }

    public Command inputCommand() {
        String input = scanner.nextLine();

        return new Command(input);
    }
}
