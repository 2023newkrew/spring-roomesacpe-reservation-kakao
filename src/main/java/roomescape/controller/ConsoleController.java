package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.connection.ConnectionManager;
import roomescape.console.view.ResultView;
import roomescape.dao.reservation.ConsoleReservationDAO;
import roomescape.dao.reservation.ReservationDAO;
import roomescape.dao.theme.ConsoleThemeDAO;
import roomescape.dao.theme.ThemeDAO;
import roomescape.dto.Reservation;
import roomescape.dto.Theme;
import roomescape.service.reservation.ReservationService;
import roomescape.service.reservation.ReservationServiceInterface;
import roomescape.service.theme.ThemeService;
import roomescape.service.theme.ThemeServiceInterface;

public class ConsoleController {

    private final ReservationServiceInterface reservationService;
    private final ThemeServiceInterface themeService;

    public ConsoleController(ConnectionManager connectionManager) {
        ReservationDAO reservationDAO = new ConsoleReservationDAO(connectionManager);
        ThemeDAO themeDAO = new ConsoleThemeDAO(connectionManager);
        reservationService = new ReservationService(reservationDAO, themeDAO);
        themeService = new ThemeService(reservationDAO, themeDAO);
    }

    private long getId(String input) {
        String params = input.split(" ")[1];

        return Long.parseLong(params.split(",")[0]);
    }

    private Reservation getReservation(String input) {
        String params = input.split(" ")[1];

        String date = params.split(",")[0];
        String time = params.split(",")[1];
        String name = params.split(",")[2];
        long theme_id = Long.parseLong(params.split(",")[3]);

        return new Reservation(
                LocalDate.parse(date),
                LocalTime.parse(time + ":00"),
                name,
                theme_id);
    }

    private Theme getTheme(String input) {
        String params = input.split(" ")[1];

        String name = params.split(",")[0];
        String desc = params.split(",")[1];
        int price = Integer.parseInt(params.split(",")[2]);

        return new Theme(name, desc, price);
    }

    public void createReservation(String input) {
        Reservation reservation = getReservation(input);
        long id = reservationService.create(reservation);
        Theme theme = themeService.find(reservation.getThemeId());

        ResultView.printCreateReservation(id, reservation, theme);
    }

    public void findReservation(String input) {
        long id = getId(input);
        Reservation reservation = reservationService.find(id);
        Theme theme = themeService.find(reservation.getThemeId());

        ResultView.printReservationInformation(id, reservation, theme);
    }

    public void removeReservation(String input) {
        long id = getId(input);
        reservationService.remove(id);

        ResultView.printRemoveReservation();
    }

    public void createTheme(String input) {
        Theme theme = getTheme(input);
        Long id = themeService.create(theme);

        ResultView.printCreateTheme(theme, id);
    }

    public void findTheme(String input) {
        long id = getId(input);
        Theme theme = themeService.find(id);

        ResultView.printThemeInformation(id, theme);
    }

    public void listTheme() {
        ResultView.printListTheme(themeService.list());
    }

    public void removeTheme(String input) {
        long id = getId(input);

        themeService.remove(id);
        ResultView.printRemoveTheme();
    }
}
