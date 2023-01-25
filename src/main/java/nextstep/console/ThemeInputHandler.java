package nextstep.console;

import nextstep.domain.Theme;
import nextstep.dto.ThemeDto;
import nextstep.service.ThemeService;

public class ThemeInputHandler implements InputHandler {

    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    static final String INPUT_PREFIX = "테마 ";
    private final ThemeService themeService;

    public ThemeInputHandler(ThemeService themeService) {
        this.themeService = themeService;
    }

    public boolean supports(String input) {
        return input.startsWith(INPUT_PREFIX);
    }

    public void handle(String input) {
        String command = input.split(" ")[1];
        String params = input.split(" ")[2];
        if (command.startsWith(ADD)) {
            themeSave(params);
            return;
        }
        if (command.startsWith(UPDATE)) {
            themeModify(params);
            return;
        }
        if (command.startsWith(FIND)) {
            themeDetails(params);
            return;
        }
        if (command.startsWith(DELETE)) {
            themeRemove(params);
            return;
        }
        Printer.printInvalidInputErrorMessage();
    }

    private void themeSave(String params) {
        try {
            ThemeDto themeDto = new ThemeDto(params);
            themeService.save(themeDto);
            Printer.printThemeConfirmMessage();
        } catch (NumberFormatException e) {
            Printer.printInvalidInputErrorMessage();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void themeDetails(String params) {
        try {
            Long themeId = Long.parseLong(params);
            Theme theme = themeService.findById(themeId);
            Printer.printThemeInfo(theme);
        } catch (RuntimeException e) {
            Printer.printErrorMessage(e);
        }
    }

    private void themeModify(String params) {
        try {
            Long themeId = Long.parseLong(params.split(",")[0]);
            String dtoParams = params.substring(params.indexOf(",") + 1);
            ThemeDto themeDto = new ThemeDto(dtoParams);
            themeService.update(themeId, themeDto);
        } catch (NumberFormatException e) {
            Printer.printInvalidInputErrorMessage();
        } catch (RuntimeException e) {
            Printer.printErrorMessage(e);
        }
    }

    private void themeRemove(String params) {
        try {
            Long themeId = Long.parseLong(params.split(",")[0]);
            themeService.deleteById(themeId);
            Printer.printReservationCancelMessage();
        } catch (RuntimeException e) {
            Printer.printErrorMessage(e);
        }
    }

}
