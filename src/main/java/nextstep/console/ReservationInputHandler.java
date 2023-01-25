package nextstep.console;

import nextstep.domain.Reservation;
import nextstep.dto.ReservationCreateRequest;
import nextstep.service.ReservationService;

public class ReservationInputHandler implements InputHandler {

    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    static final String INPUT_PREFIX = "예약 ";
    private final ReservationService reservationService;

    public ReservationInputHandler(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public boolean supports(String input) {
        return input.startsWith(INPUT_PREFIX);
    }

    public void handle(String input) {
        String command = input.split(" ")[1];
        String params = input.split(" ")[2];
        if (command.startsWith(ADD)) {
            reservationSave(params);
            return;
        }
        if (command.startsWith(FIND)) {
            reservationDetails(params);
            return;
        }
        if (command.startsWith(DELETE)) {
            reservationRemove(params);
            return;
        }
        Printer.printInvalidInputErrorMessage();
    }

    private void reservationSave(String params) {
        ReservationCreateRequest reservationRequest = new ReservationCreateRequest(params);

        try {
            Reservation reservation = reservationService.save(reservationRequest);
            Printer.printReservationConfirmMessage(reservation);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void reservationDetails(String params) {
        try {
            Long roomId = Long.parseLong(params);

            Reservation reservation = reservationService.findById(roomId);
            Printer.printReservationInfo(reservation);
        } catch (RuntimeException e) {
            Printer.printErrorMessage(e);
        }
    }

    private void reservationRemove(String params) {
        try {
        Long roomId = Long.parseLong(params.split(",")[0]);

            reservationService.deleteById(roomId);
            Printer.printReservationCancelMessage();
        } catch (RuntimeException e) {
            Printer.printErrorMessage(e);
        }
    }
}
