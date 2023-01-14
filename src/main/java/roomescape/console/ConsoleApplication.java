package roomescape.console;

import roomescape.controller.ConsoleReservationController;
import roomescape.domain.Reservation;
import roomescape.repository.Reservation.JdbcReservationRepository;
import roomescape.service.Reservation.WebReservationService;

import java.util.Scanner;

import static roomescape.utils.Messages.*;

public class ConsoleApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";
    public static void main(String[] args) throws ClassNotFoundException {
        ConsoleReservationController consoleReservationController = new ConsoleReservationController(
                new WebReservationService(new JdbcReservationRepository())
        );
        InputParsing inputParsing = new InputParsing();
        Scanner scanner = new Scanner(System.in);
        long reservationIndex = 0L; // 마지막 ID 값 가져오기
        while (true) {
            System.out.println();
            System.out.println(CONSOLE_DESCRIPTION.getMessage());
            String input = scanner.nextLine();
            if (input.startsWith(ADD)) {
                Reservation reservationInfo = inputParsing.createReserveInfo(++reservationIndex, input);
                Reservation reservation = consoleReservationController.createReservation(reservationInfo);
                System.out.println(RESERVATION_REGISTERED.getMessage());
                getReservationInfo(reservation);
            }
            if (input.startsWith(FIND)) {
                long findId = inputParsing.getFindId(input);
                Reservation reservation = consoleReservationController.lookUpReservation(findId);
                getReservationInfo(reservation);
            }
            if (input.startsWith(DELETE)) {
                long deleteId = inputParsing.getFindId(input);
                boolean b = consoleReservationController.deleteReservation(deleteId);
                System.out.println(RESERVATION_CANCEL.getMessage());
            }
            if (input.equals(QUIT)) {
                break;
            }
        }
    }

    private static void getReservationInfo(Reservation reservation){
        System.out.println(RESERVATION_ID.getMessage() + reservation.getId());
        System.out.println(RESERVATION_DATE.getMessage() + reservation.getDate().toString());
        System.out.println(RESERVATION_TIME.getMessage() + reservation.getTime().toString());
        System.out.println(RESERVATION_NAME.getMessage() + reservation.getName());
        System.out.println(RESERVATION_THEME_ID.getMessage() + reservation.getThemeId().toString());
    }
}
