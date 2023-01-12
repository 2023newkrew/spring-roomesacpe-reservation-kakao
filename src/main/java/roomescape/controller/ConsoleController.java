package roomescape.controller;

import org.springframework.stereotype.Controller;
import roomescape.dto.*;
import roomescape.service.ReservationService;
import roomescape.service.ThemeService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class ConsoleController {
    private final ReservationService reservationService;
    private final ThemeService themeService;

    public ConsoleController(ReservationService reservationService, ThemeService themeService) {
        this.reservationService = reservationService;
        this.themeService = themeService;
    }

    public Optional<ReservationResponseDto> createReservation(String input) {
        String[] params = splitParams(input);
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
        String[] params = splitParams(input);
        Long id = Long.parseLong(params[0]);
        Optional<ReservationResponseDto> res = Optional.empty();
        try {
            res = Optional.ofNullable(reservationService.findReservation(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public Boolean cancelReservation(String input) {
        String[] params = splitParams(input);
        Long id = Long.parseLong(params[0]);
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

    public Optional<ThemeResponseDto> createTheme(String input) {
        String[] params = splitParams(input);
        String name = params[0];
        String desc = params[1];
        Integer price = Integer.valueOf(params[2]);
        ThemeRequestDto req = new ThemeRequestDto(name, desc, price);
        Optional<ThemeResponseDto> res = Optional.empty();
        try {
            res = Optional.ofNullable(themeService.createTheme(req));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public Optional<ThemesResponseDto> findThemes() {
        Optional<ThemesResponseDto> res = Optional.empty();
        try {
            res = Optional.ofNullable(themeService.findThemes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public Boolean deleteTheme(String input) {
        String[] params = splitParams(input);
        Long id = Long.parseLong(params[0]);
        Boolean res;
        try {
            themeService.deleteTheme(id);
            res = true;
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    // 이름, 설명 등의 공백은 유지
    private String[] splitParams(String input) {
        String[] splitted = input.split(" ");
        String[] subArray = Arrays.copyOfRange(splitted, 2, splitted.length);
        return Arrays.stream(String.join(" ", subArray).split(","))
                .map(String::strip)
                .toArray(String[]::new);
    }
}
