package roomescape.controller;

public class ConsoleControllerMapper {

    private static final String RESERVATION_ADD = "res_add";
    private static final String RESERVATION_FIND = "res_find";
    private static final String RESERVATION_DELETE = "res_delete";
    private static final String THEME_ADD = "the_add";
    private static final String THEME_FIND = "the_find";
    private static final String THEME_LIST = "the_list";
    private static final String THEME_DELETE = "the_delete";
    private static final String QUIT = "quit";

    private final ConsoleController consoleController;

    public ConsoleControllerMapper(ConsoleController consoleController) {
        this.consoleController = consoleController;
    }

    public void executeCommand(String input) {
        if (input.startsWith(RESERVATION_ADD)) {
            consoleController.createReservation(input);
            return;
        }

        if (input.startsWith(RESERVATION_FIND)) {
            consoleController.findReservation(input);
            return;
        }

        if (input.startsWith(RESERVATION_DELETE)) {
            consoleController.removeReservation(input);
            return;
        }

        if(input.startsWith(THEME_ADD)) {
            consoleController.createTheme(input);
            return;
        }

        if(input.startsWith(THEME_FIND)) {
            consoleController.findTheme(input);
            return;
        }

        if(input.startsWith(THEME_LIST)) {
            consoleController.listTheme();
            return;
        }

        if(input.startsWith(THEME_DELETE)) {
            consoleController.removeTheme(input);
        }
    }

    public boolean isRepeat(String input) {
        return !input.equals(QUIT);
    }
}
