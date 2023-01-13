package nextstep.console.controller;

import nextstep.console.view.ConsoleReservationView;
import nextstep.domain.repository.ReservationRepository;
import nextstep.domain.repository.ThemeRepository;
import nextstep.dto.CreateReservationRequest;
import nextstep.repository.JdbcReservationRepository;
import nextstep.repository.JdbcThemeRepository;
import nextstep.service.ReservationService;
import nextstep.utils.DataSourceUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import static nextstep.console.ConsoleCommands.*;
import static nextstep.console.ConsoleCommands.BACK;

public class ConsoleReservationController {
    private final ConsoleReservationView reservationView;
    private final ReservationService reservationService;

    public ConsoleReservationController() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceUtil.getDataSource());
        ReservationRepository reservationRepository = new JdbcReservationRepository(jdbcTemplate);
        ThemeRepository themeRepository = new JdbcThemeRepository(jdbcTemplate);

        this.reservationView = new ConsoleReservationView();
        this.reservationService = new ReservationService(reservationRepository, themeRepository);
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

        Long reservationId = reservationService.createReservation(reservationRequest);

        reservationView.printAddMessage();
        reservationView.printReservation(reservationService.findReservationById(reservationId));
    }

    private void findProcess(String input) {
        String params = input.split(" ")[1];
        Long id = Long.parseLong(params.split(",")[0]);
        reservationView.printReservation(reservationService.findReservationById(id));
    }

    private void deleteProcess(String input) {
        String params = input.split(" ")[1];

        Long id = Long.parseLong(params.split(",")[0]);

        if (reservationService.deleteReservationById(id))
            reservationView.printDeleteMessage();
    }
}
