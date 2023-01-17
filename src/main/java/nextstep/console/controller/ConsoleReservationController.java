package nextstep.console.controller;

import nextstep.console.repository.ConsoleReservationRepository;
import nextstep.console.repository.ConsoleThemeRepository;
import nextstep.console.view.ConsoleReservationView;
import nextstep.domain.ReservationManager;
import nextstep.dto.CreateReservationRequest;

import static nextstep.console.ConsoleCommands.*;
import static nextstep.console.ConsoleCommands.BACK;

public class ConsoleReservationController {
    private final ConsoleReservationView reservationView;
    private final ReservationManager reservationManager;

    public ConsoleReservationController() {
        this.reservationView = new ConsoleReservationView();
        this.reservationManager = new ReservationManager(
                new ConsoleReservationRepository(),
                new ConsoleThemeRepository()
        );
    }

    public void run() {
        while(true) {
            reservationView.printCommand();
            String input = reservationView.inputCommand();

            if (input.startsWith(ADD))
                addProcess(input);

            if (input.startsWith(FIND))
                findProcess(input);

            if (input.startsWith(DELETE))
                deleteProcess(input);

            if(input.startsWith(BACK))
                return;
        }
    }

    private void addProcess(String input) {
        String params = input.split(" ")[1];

        String date = params.split(",")[0];
        String time = params.split(",")[1];
        String name = params.split(",")[2];
        String themeId = params.split(",")[3];

        CreateReservationRequest reservationRequest = new CreateReservationRequest(date, time + ":00", name, Long.parseLong(themeId));

        Long reservationId = reservationManager.createReservation(reservationRequest);

        reservationView.printAddMessage();
        reservationView.printReservation(reservationManager.findReservationById(reservationId));
    }

    private void findProcess(String input) {
        String params = input.split(" ")[1];
        Long id = Long.parseLong(params.split(",")[0]);
        reservationView.printReservation(reservationManager.findReservationById(id));
    }

    private void deleteProcess(String input) {
        String params = input.split(" ")[1];

        Long id = Long.parseLong(params.split(",")[0]);

        if (reservationManager.deleteReservationById(id))
            reservationView.printDeleteMessage();
    }
}
