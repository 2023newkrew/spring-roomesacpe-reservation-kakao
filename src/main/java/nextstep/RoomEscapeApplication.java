package nextstep;

import nextstep.dto.CreateReservationRequest;
import nextstep.exception.DuplicateReservationException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.repository.ReservationH2Repository;
import nextstep.repository.ReservationRepository;
import nextstep.service.RoomEscapeService;

import java.util.Scanner;

public class RoomEscapeApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReservationRepository repository = new ReservationH2Repository();
        RoomEscapeService roomEscapeService = new RoomEscapeService(repository);

        while (true) {
            Printer.printGuideMessage();
            String input = scanner.nextLine();
            if (input.startsWith(ADD)) {
                String params = input.split(" ")[1];

                String date = params.split(",")[0];
                String time = params.split(",")[1];
                String name = params.split(",")[2];
                CreateReservationRequest reservationRequest = new CreateReservationRequest(date, time, name);

                try {
                    Reservation reservation = roomEscapeService.add(reservationRequest);
                    Printer.printReservationConfirmMessage(reservation);
                } catch (DuplicateReservationException e) {
                    System.out.println(e.getMessage());
                }

            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long roomId = Long.parseLong(params.split(",")[0]);

                try {
                    Reservation reservation = roomEscapeService.get(roomId);
                    Printer.printReservationInfo(reservation);
                } catch (ReservationNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long roomId = Long.parseLong(params.split(",")[0]);

                roomEscapeService.delete(roomId);

                Printer.printReservationCancelMessage();
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}
