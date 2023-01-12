package nextstep;

import nextstep.domain.Reservation;
import nextstep.domain.ReservationSaveForm;
import nextstep.domain.Theme;
import nextstep.service.ReservationService;
import nextstep.service.ThemeService;

import java.time.LocalDate;
import java.time.LocalTime;

public class ConsoleRoomEscapeManager {
    private final ReservationService reservationService;
    private final ThemeService themeService;

    public ConsoleRoomEscapeManager(ReservationService reservationService, ThemeService themeService) {
        this.reservationService = reservationService;
        this.themeService = themeService;
    }

    public void addReservation(String input) {
        String params = input.split(" ")[1];

        String date = params.split(",")[0];
        String time = params.split(",")[1];
        String name = params.split(",")[2];
        Long themeId = Long.parseLong(params.split(",")[3]);

        ReservationSaveForm reservationSaveForm = new ReservationSaveForm(
                LocalDate.parse(date),
                LocalTime.parse(time + ":00"),
                name,
                themeId
        );
        Long id = reservationService.saveReservation(reservationSaveForm);

        System.out.println("예약이 등록되었습니다.");
        printReservation(reservationService.findReservation(id));
    }

    public void findReservation(String input) {
        String params = input.split(" ")[1];

        Long id = Long.parseLong(params.split(",")[0]);

        Reservation reservation = reservationService.findReservation(id);
        printReservation(reservation);
    }

    public void deleteReservation(String input) {
        String params = input.split(" ")[1];

        Long id = Long.parseLong(params.split(",")[0]);

        reservationService.deleteReservation(id);
        System.out.println("예약이 취소되었습니다.");
    }

    public void addTheme(String input) {
        String params = input.split(" ")[1];

        String name = params.split(",")[0];
        String desc = params.split(",")[1];
        int price = Integer.parseInt(params.split(",")[2]);

        Theme theme = new Theme(name, desc, price);
        Long id = themeService.saveTheme(theme);

        System.out.println("테마 등록되었습니다.");
        printTheme(new Theme(
                id,
                theme.getName(),
                theme.getDesc(),
                theme.getPrice()
        ));
    }

    public void findAllTheme(String input) {
        themeService.findAllTheme()
                .stream()
                .forEach(this::printTheme);
    }

    public void deleteTheme(String input) {
        String params = input.split(" ")[1];

        Long id = Long.parseLong(params.split(",")[0]);

        themeService.deleteTheme(id);
        System.out.println("테마가 삭제되었습니다.");
    }

    private void printReservation(Reservation reservation) {
        System.out.println("예약 번호: " + reservation.getId());
        System.out.println("예약 날짜: " + reservation.getDate());
        System.out.println("예약 시간: " + reservation.getTime());
        System.out.println("예약자 이름: " + reservation.getName());
        System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
        System.out.println("예약 테마 설명: " + reservation.getTheme().getDesc());
        System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
        System.out.println();
    }

    private void printTheme(Theme theme) {
        System.out.println("테마 번호: " + theme.getId());
        System.out.println("테마 이름: " + theme.getName());
        System.out.println("테마 설명: " + theme.getDesc());
        System.out.println("테마 가격: " + theme.getPrice());
        System.out.println();
    }
}
