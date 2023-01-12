package roomescape.controller;

import org.springframework.stereotype.Controller;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Controller
public class ConsoleController {
    private final ReservationService reservationService;

    public ConsoleController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public Optional<ReservationResponseDto> createReservation(String input) {
        String[] params = input.split(" ")[1].split(",");
        LocalDate date = LocalDate.parse(params[0]);
        LocalTime time = LocalTime.parse(params[1] + ":00");
        String name = params[2];
        Long themeId = Long.valueOf(params[3]);
        ReservationRequestDto req = new ReservationRequestDto(date, time, name, themeId);
        Optional<ReservationResponseDto> res = Optional.empty();
        try {
            res = Optional.ofNullable(reservationService.createReservation(req));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public Optional<ReservationResponseDto> findReservation(String input) {
        String params = input.split(" ")[1];
        Long id = Long.parseLong(params.split(",")[0]);
        Optional<ReservationResponseDto> res = Optional.empty();
        try {
            res = Optional.ofNullable(reservationService.findReservation(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public Boolean cancelReservation(String input) {
        String params = input.split(" ")[1];
        Long id = Long.parseLong(params.split(",")[0]);
        Boolean res;
        try {
            reservationService.cancelReservation(id);
            res = true;
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }
}
