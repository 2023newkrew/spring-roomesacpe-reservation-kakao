package roomescape;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import roomescape.controller.ConsoleController;

enum Command {
    ADD_RESERVATION,
    FIND_RESERVATION,
    DELETE_RESERVATION,
    ADD_THEME,
    SHOW_THEME,
    DELETE_THEME,
    QUIT;
}

@Component("consoleApplication")
@ConditionalOnProperty(value = "app.console.enabled")
public class ConsoleApplication {
    private final ConsoleController consoleController;

    public ConsoleApplication(ConsoleController consoleController) {
        this.consoleController = consoleController;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run(ApplicationReadyEvent event) {
        Command cmd = null;
        String[] params;
        while (cmd == null || !cmd.equals(Command.QUIT)) {
            try {
                String input = consoleController.getInput();
                cmd = Command.valueOf(consoleController.getCommand(input));
                params = consoleController.getParams(input);
            } catch (Exception e) {
                consoleController.receivedInvalidInput();
                continue;
            }
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
                    consoleController.close();
                    break;
                default:
                    consoleController.receivedInvalidInput();
                    break;
            }
        }
        event.getApplicationContext().close();
    }
}
