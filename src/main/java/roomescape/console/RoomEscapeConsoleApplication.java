package roomescape.console;

import javax.sql.DataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import roomescape.connection.ConnectionManager;
import roomescape.console.view.InputView;
import roomescape.controller.ConsoleController;
import roomescape.controller.ConsoleControllerMapper;

@Profile("!test")
@Component
public class RoomEscapeConsoleApplication implements CommandLineRunner {

    private final ConsoleControllerMapper consoleControllerMapper;

    public RoomEscapeConsoleApplication(DataSource dataSource) {
        ConnectionManager connectionManager = new ConnectionManager(dataSource);
        ConsoleController consoleController = new ConsoleController(connectionManager);
        consoleControllerMapper = new ConsoleControllerMapper(consoleController);
    }

    @Override
    public void run(String... args) {
        boolean repeat = true;
        while (repeat) {
            String input = InputView.getCommand();
            consoleControllerMapper.executeCommand(input);
            repeat = consoleControllerMapper.isRepeat(input);
        }
    }
}
