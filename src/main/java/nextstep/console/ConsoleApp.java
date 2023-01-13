package nextstep.console;

import static nextstep.console.ConsoleCommand.QUIT;

import java.sql.SQLException;
import nextstep.console.controller.ReservationConsoleController;
import nextstep.console.controller.ThemeConsoleController;
import nextstep.console.view.View;
import nextstep.dto.ConnectionHandler;
import org.springframework.stereotype.Component;

@Component
public class ConsoleApp {

    private static final ConnectionHandler connectionHandler = new ConnectionHandler();

    private static final View view = new View();
    private static final ReservationConsoleController RESERVATION_CONSOLE_CONTROLLER = new ReservationConsoleController(
            connectionHandler, view);
    private static final ThemeConsoleController themeConsole = new ThemeConsoleController(connectionHandler, view);

    public static void run(){

        while (true) {
            try {
                view.printCommand();
                String input = view.readLine();
                RESERVATION_CONSOLE_CONTROLLER.executeReservationCommand(input);
                if (input.startsWith("theme")) {
                    themeConsole.executeThemeCommand(input);
                }
                if (input.equals(QUIT)) {
                    connectionHandler.release();
                    System.exit(0);
                }
            } catch(IllegalArgumentException e){
                view.printErrorMessage(e, "형식에 맞춰 입력해주세요.");
            } catch (NullPointerException e) {
                view.printErrorMessage(e, "해당하는 값이 존재하지 않습니다.");
            }catch (Exception e) {
                view.printErrorMessage(e);
            }

        }
    }
}



