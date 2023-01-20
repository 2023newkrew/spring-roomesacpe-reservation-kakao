package roomescape.console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import roomescape.connection.ConnectionManager;
import roomescape.connection.ConnectionSetting;
import roomescape.connection.PoolSetting;
import roomescape.console.view.InputView;
import roomescape.controller.ConsoleController;
import roomescape.controller.ConsoleControllerMapper;

@Profile("!test")
@Component
public class RoomEscapeConsoleApplication implements CommandLineRunner {


    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static final ConnectionSetting CONNECTION_SETTING = new ConnectionSetting(URL, USER, PASSWORD);
    private static final PoolSetting CONNECTION_POOL_SETTING = new PoolSetting(10);

    private final ConnectionManager connectionManager = new ConnectionManager(
            CONNECTION_SETTING, CONNECTION_POOL_SETTING);
    private final ConsoleController consoleController = new ConsoleController(connectionManager);
    private final ConsoleControllerMapper consoleControllerMapper = new ConsoleControllerMapper(consoleController);

    @Override
    public void run(String... args) {
        boolean repeat = true;
        while (repeat) {
            String input = InputView.getCommand();
            consoleControllerMapper.executeCommand(input);
            repeat = consoleControllerMapper.isRepeat(input);
        }
        connectionManager.close();
    }
}
