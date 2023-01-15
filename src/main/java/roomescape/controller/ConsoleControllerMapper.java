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

    public static void executeCommand(String input) {
        if (input.startsWith(RESERVATION_ADD)) {
            ConsoleController.createReservation(input);
        }

        if (input.startsWith(RESERVATION_FIND)) {
            ConsoleController.findReservation(input);
        }

        if (input.startsWith(RESERVATION_DELETE)) {
            ConsoleController.removeReservation(input);
        }

        if(input.startsWith(THEME_ADD)) {
            ConsoleController.createTheme(input);
        }

        if(input.startsWith(THEME_FIND)) {
            ConsoleController.findTheme(input);
        }

        if(input.startsWith(THEME_LIST)) {
            ConsoleController.listTheme();
        }

        if(input.startsWith(THEME_DELETE)) {
            ConsoleController.removeTheme(input);
        }
    }

    public static boolean isRepeat(String input) {
        return !input.equals(QUIT);
    }
}
