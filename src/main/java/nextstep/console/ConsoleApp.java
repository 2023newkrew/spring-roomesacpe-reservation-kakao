package nextstep.console;

import static nextstep.console.ConsoleCommand.QUIT;

import nextstep.console.config.ConsoleConfig;
import nextstep.console.controller.ReservationConsoleController;
import nextstep.console.controller.ThemeConsoleController;
import nextstep.console.utils.ConnectionHandler;
import nextstep.console.view.View;

public class ConsoleApp {

    private static final ConnectionHandler connectionHandler = new ConnectionHandler();

    private static final View view = ConsoleConfig.getInstance().getView();

    private static final ReservationConsoleController reservationConsoleController = ConsoleConfig.getInstance()
            .getReservationConsoleController();
    private static final ThemeConsoleController themeConsoleController = ConsoleConfig.getInstance()
            .getThemeConsoleController();

    public static void run(){

        while (true) {
            try {
                view.printCommand();
                String input = view.readLine();
                reservationConsoleController.executeReservationCommand(input);
                if (input.startsWith("theme")) {
                    themeConsoleController.executeThemeCommand(input);
                }
                if (input.equals(QUIT)) {
                    connectionHandler.release();
                    System.exit(0);
                }
            } catch(IllegalArgumentException e){
                view.printErrorMessage(e, "형식에 맞춰 입력해주세요.");
            } catch (NullPointerException e) {
                view.printErrorMessage(e, "해당하는 값이 존재하지 않습니다.");
            }
            catch (Exception e) {
                view.printErrorMessage(e);
            }

        }
    }
}



