package nextstep.reservation.dto;

import lombok.Builder;
import nextstep.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;

    private Theme theme;

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    public Reservation(Long id, ReservationRequestDto reservationRequestDto) {
        this.id = id;
        this.date = reservationRequestDto.getDate();
        this.time = reservationRequestDto.getTime();
        this.name = reservationRequestDto.getName();
        this.theme = new Theme("워너고홈 ", "병맛 어드벤처 회사 코믹물", 29000);
    }


    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public Theme getTheme() {
        return theme;
    }
}
