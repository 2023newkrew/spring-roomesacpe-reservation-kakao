package roomescape.console;

import org.springframework.stereotype.Controller;
import roomescape.dto.*;
import roomescape.service.ReservationService;
import roomescape.service.ThemeService;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class ConsoleController {
    private final ReservationService reservationService;
    private final ThemeService themeService;
    private final ConsoleView consoleView;

    public ConsoleController(ReservationService reservationService, ThemeService themeService, ConsoleView consoleView) {
        this.reservationService = reservationService;
        this.themeService = themeService;
        this.consoleView = consoleView;
    }

    public void createReservation(String[] params) {
        LocalDate date = LocalDate.parse(params[0]);
        LocalTime time = LocalTime.parse(params[1] + ":00");
        String name = params[2];
        Long themeId = Long.valueOf(params[3]);
        ReservationRequestDto req = new ReservationRequestDto(date, time, name, themeId);
        ReservationResponseDto res;
        try {
            res = reservationService.createReservation(req);
        } catch (Exception e) {
            consoleView.showErrorMessage(ConsoleView.FAILED_TO_CREATE_RESERVATION, e.getMessage());
            return;
        }
        consoleView.showCreatedReservation(res);
    }

    public void findReservation(String[] params) {
        Long id = Long.parseLong(params[0]);
        ReservationResponseDto res;
        try {
            res = reservationService.findReservation(id);
        } catch (Exception e) {
            consoleView.showErrorMessage(ConsoleView.FAILED_TO_FIND_RESERVATION, e.getMessage());
            return;
        }
        consoleView.showFoundReservation(res);
    }

    public void cancelReservation(String[] params) {
        Long id = Long.parseLong(params[0]);
        try {
            reservationService.cancelReservation(id);
        } catch (Exception e) {
            consoleView.showErrorMessage(ConsoleView.FAILED_TO_CANCEL_RESERVATION, e.getMessage());
            return;
        }
        consoleView.showReservationCanceled();
    }

    public void createTheme(String[] params) {
        String name = params[0];
        String desc = params[1];
        Integer price = Integer.valueOf(params[2]);
        ThemeRequestDto req = new ThemeRequestDto(name, desc, price);
        ThemeResponseDto res;
        try {
            res = themeService.createTheme(req);
        } catch (Exception e) {
            consoleView.showErrorMessage(ConsoleView.FAILED_TO_CREATE_THEME, e.getMessage());
            return;
        }
        consoleView.showCreatedTheme(res);
    }

    public void findThemes() {
        ThemesResponseDto res;
        try {
            res = themeService.findThemes();
        } catch (Exception e) {
            consoleView.showErrorMessage(ConsoleView.FAILED_TO_SHOW_THEME, e.getMessage());
            return;
        }
        consoleView.showThemes(res);
    }

    public void deleteTheme(String[] params) {
        Long id = Long.parseLong(params[0]);
        try {
            themeService.deleteTheme(id);
        } catch (Exception e) {
            consoleView.showErrorMessage(ConsoleView.FAILED_TO_DELETE_THEME, e.getMessage());
            return;
        }
        consoleView.showThemeDeleted();
    }
}
