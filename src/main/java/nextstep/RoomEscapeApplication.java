package nextstep;

import nextstep.console.Printer;
import nextstep.domain.Reservation;
import nextstep.dto.ReservationCreateRequest;
import nextstep.exception.DuplicateReservationException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.repository.ReservationH2Repository;
import nextstep.repository.ReservationRepository;
import nextstep.service.ReservationService;

import java.util.Scanner;

public class RoomEscapeApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReservationRepository repository = new ReservationH2Repository();
        ReservationService reservationService = new ReservationService(repository);

        while (true) {
            Printer.printGuideMessage();
            String input = scanner.nextLine();
            if (input.startsWith(ADD)) {
                String params = input.split(" ")[1];

                String date = params.split(",")[0];
                String time = params.split(",")[1];
                String name = params.split(",")[2];
                ReservationCreateRequest reservationRequest = new ReservationCreateRequest(date, time, name);

                try {
                    Reservation reservation = reservationService.add(reservationRequest);
                    Printer.printReservationConfirmMessage(reservation);
                } catch (DuplicateReservationException e) {
                    System.out.println(e.getMessage());
                }

            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long roomId = Long.parseLong(params.split(",")[0]);

                try {
                    Reservation reservation = reservationService.findById(roomId);
                    Printer.printReservationInfo(reservation);
                } catch (ReservationNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long roomId = Long.parseLong(params.split(",")[0]);

                reservationService.deleteById(roomId);

                Printer.printReservationCancelMessage();
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}
