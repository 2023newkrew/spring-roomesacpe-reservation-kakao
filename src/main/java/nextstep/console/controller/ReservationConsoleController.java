package nextstep.console.controller;

import static nextstep.console.ConsoleCommand.ADD;
import static nextstep.console.ConsoleCommand.DELETE;
import static nextstep.console.ConsoleCommand.FIND;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.console.view.View;
import nextstep.console.utils.ConnectionHandler;
import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ThemeResponseDto;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;
import nextstep.repository.ReservationJdbcRepositoryImpl;
import nextstep.repository.ThemeJdbcRepositoryImpl;
import nextstep.service.ReservationService;
import nextstep.service.ReservationServiceImpl;
import nextstep.service.ThemeService;
import nextstep.service.ThemeServiceImpl;

public class ReservationConsoleController {

    private final ReservationService reservationService;

    private final ThemeService themeService;

    private final View view;

    public ReservationConsoleController(ConnectionHandler connectionHandler, View view) {
        this.reservationService = new ReservationServiceImpl(new ReservationJdbcRepositoryImpl(connectionHandler));
        this.themeService = new ThemeServiceImpl(new ThemeJdbcRepositoryImpl(connectionHandler));
        this.view = view;
    }

    public void executeReservationCommand(String input){
        if (input.startsWith(ADD)) {
            view.printReservation(makeReservation(input));
        }
        if (input.startsWith(FIND)) {
            Long id = parseId(input);
            view.printReservationResponseDto(reservationService.findReservation(id));
        }
        if (input.startsWith(DELETE)) {
            Long id = parseId(input);
            reservationService.deleteById(id);
        }
    }

    private Long parseId(String input) {
        String params = parseParams(input);
        return Long.parseLong(params.split(",")[0]);
    }

    private String parseParams(String input) {
        return input.split(" ")[1];
    }


    private Reservation makeReservation(String input){
        String params = parseParams(input);
        String date = params.split(",")[0];
        String time = params.split(",")[1];
        String name = params.split(",")[2];
        String themeId = params.split(",")[3];
        Long id = reservationService.createReservation(
                new ReservationRequestDTO(LocalDate.parse(date), LocalTime.parse(time + ":00"), name, 1L));
        ThemeResponseDto themeResponseDto = themeService.findTheme(Long.parseLong(themeId));
        return new Reservation(id, LocalDate.parse(date), LocalTime.parse(time + ":00"), name,
                new Theme(themeResponseDto.getName(), themeResponseDto.getDescription(), themeResponseDto.getPrice()));
    }
}
