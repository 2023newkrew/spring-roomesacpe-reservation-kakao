package roomescape.console;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("consoleApplication")
@ConditionalOnProperty(value = "app.console.enabled")
public class ConsoleApplication {
    private final ConsoleController consoleController;
    private final ConsoleInputHandler consoleInputHandler;

    public ConsoleApplication(ConsoleController consoleController, ConsoleInputHandler consoleInputHandler) {
        this.consoleController = consoleController;
        this.consoleInputHandler = consoleInputHandler;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run(ApplicationReadyEvent event) {
        Command cmd = null;
        while (cmd == null || !cmd.equals(Command.QUIT)) {
            cmd = consoleInputHandler.getInput();
            String[] params = cmd.getParams();
            switch (cmd) {
                case ADD_RESERVATION:
                    consoleController.createReservation(params);
                    break;
                case FIND_RESERVATION:
                    consoleController.findReservation(params);
                    break;
                case DELETE_RESERVATION:
                    consoleController.cancelReservation(params);
                    break;
                case ADD_THEME:
                    consoleController.createTheme(params);
                    break;
                case SHOW_THEME:
                    consoleController.findThemes();
                    break;
                case DELETE_THEME:
                    consoleController.deleteTheme(params);
                    break;
                case QUIT:
                    consoleInputHandler.close();
                    break;
                default:
                    consoleInputHandler.receivedInvalidInput();
                    break;
            }
        }
        event.getApplicationContext().close();
    }
}
