package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.console.view.ResultView;
import roomescape.dao.reservation.ConsoleReservationDAO;
import roomescape.dao.reservation.ReservationDAO;
import roomescape.dao.theme.ConsoleThemeDAO;
import roomescape.dao.theme.ThemeDAO;
import roomescape.dto.Reservation;
import roomescape.dto.Theme;
import roomescape.service.reservation.ReservationService;
import roomescape.service.theme.ThemeService;

public class ConsoleController {

    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static final ReservationDAO RESERVATION_DAO
            = new ConsoleReservationDAO(URL, USER, PASSWORD);
    private static final ThemeDAO THEME_DAO
            = new ConsoleThemeDAO(URL, USER, PASSWORD);

    private static final ReservationService reservationService
            = new ReservationService(RESERVATION_DAO, THEME_DAO);
    private static final ThemeService themeService
            = new ThemeService(RESERVATION_DAO, THEME_DAO);

    private static long getId(String input) {
        String params = input.split(" ")[1];

        return Long.parseLong(params.split(",")[0]);
    }

    private static Reservation getReservation(String input) {
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

    private static Theme getTheme(String input) {
        String params = input.split(" ")[1];

        String name = params.split(",")[0];
        String desc = params.split(",")[1];
        int price = Integer.parseInt(params.split(",")[2]);

        return new Theme(name, desc, price);
    }

    public static void createReservation(String input) {
        Reservation reservation = getReservation(input);
        long id = reservationService.create(reservation);
        Theme theme = themeService.find(reservation.getThemeId());

        ResultView.printCreateReservation(id, reservation, theme);
    }

    public static void findReservation(String input) {
        long id = getId(input);
        Reservation reservation = reservationService.find(id);
        Theme theme = themeService.find(reservation.getThemeId());

        ResultView.printReservationInformation(id, reservation, theme);
    }

    public static void removeReservation(String input) {
        long id = getId(input);
        reservationService.remove(id);

        ResultView.printRemoveReservation();
    }

    public static void createTheme(String input) {
        Theme theme = getTheme(input);
        Long id = themeService.create(theme);

        ResultView.printCreateTheme(theme, id);
    }

    public static void findTheme(String input) {
        long id = getId(input);
        Theme theme = themeService.find(id);

        ResultView.printThemeInformation(id, theme);
    }

    public static void listTheme() {
        ResultView.printListTheme(themeService.list());
    }

    public static void removeTheme(String input) {
        long id = getId(input);

        themeService.remove(id);
        ResultView.printRemoveTheme();
    }
}
