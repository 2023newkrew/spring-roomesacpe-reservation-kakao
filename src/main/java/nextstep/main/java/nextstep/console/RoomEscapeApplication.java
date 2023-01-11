package nextstep.main.java.nextstep.console;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.domain.Theme;
import nextstep.main.java.nextstep.exception.exception.DuplicateReservationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import static nextstep.main.java.nextstep.message.ExceptionMessage.DUPLICATE_RESERVATION_MESSAGE;

public class RoomEscapeApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleView consoleView = new ConsoleView();
        ReservationDAO reservationDAO = new ReservationDAO();
        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

        while (true) {
            consoleView.printCommand();

            String input = scanner.nextLine();
            if (input.startsWith(ADD)) {
                String params = input.split(" ")[1];

                String date = params.split(",")[0];
                String time = params.split(",")[1];
                String name = params.split(",")[2];

                Reservation reservation = new Reservation(
                        LocalDate.parse(date),
                        LocalTime.parse(time + ":00"),
                        name,
                        theme
                );

                if (reservationDAO.existsByDateAndTime(LocalDate.parse(date), LocalTime.parse(time))) {
                    throw new DuplicateReservationException(DUPLICATE_RESERVATION_MESSAGE);
                }

                consoleView.printRegisteredReservationInfo(reservationDAO.save(reservation));
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];
                Long id = Long.parseLong(params.split(",")[0]);

                Reservation reservation = reservationDAO.findOne(id)
                        .get();

                consoleView.printReservationInfo(reservation);
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];
                Long id = Long.parseLong(params.split(",")[0]);

                reservationDAO.deleteOne(id);

                consoleView.printCancelReservation();
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}
