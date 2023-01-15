package roomescape.console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import roomescape.console.view.InputView;
import roomescape.controller.ConsoleControllerMapper;

@Profile("!test")
@Component
public class RoomEscapeConsoleApplication implements CommandLineRunner {

    public static void main(String[] args) {
        boolean repeat = true;
        while (repeat) {
            String input = InputView.getCommand();
            ConsoleControllerMapper.executeCommand(input);
            repeat = ConsoleControllerMapper.isRepeat(input);
        }
    }

    @Override
    public void run(String... args) {
        main(args);
    }
}
