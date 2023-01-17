package nextstep.console.controller;

import static nextstep.console.ConsoleCommand.ADD;
import static nextstep.console.ConsoleCommand.DELETE;
import static nextstep.console.ConsoleCommand.FIND;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.console.view.View;
import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;
import nextstep.service.ReservationService;
import nextstep.service.ThemeService;

public class ReservationConsoleController {

    private final ReservationService reservationService;

    private final ThemeService themeService;

    private final View view;

    public ReservationConsoleController(ReservationService reservationService, ThemeService themeService, View view) {
        this.reservationService = reservationService;
        this.themeService = themeService;
        this.view = view;
    }

    public void executeReservationCommand(String input){
        if (input.startsWith(ADD)) {
            view.printReservationResponseDto(makeReservationResponseDto(input));
        }
        if (input.startsWith(FIND)) {
            Long id = parseId(input);
            view.printReservationResponseDto(ReservationResponseDTO.of(reservationService.findReservationByID(id)));
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


    private ReservationResponseDTO makeReservationResponseDto(String input){
        String params = parseParams(input);
        String date = params.split(",")[0];
        String time = params.split(",")[1];
        String name = params.split(",")[2];
        Long themeId = Long.parseLong(params.split(",")[3]);
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(LocalDate.parse(date),LocalTime.parse(time + ":00"), name, themeId);
        reservationService.validateCreateReservation(reservationRequestDTO);
        Theme theme = themeService.findById(themeId);
        Reservation reservation = reservationService.createReservation(reservationRequestDTO, theme);

        return ReservationResponseDTO.of(reservation);

    }
}
