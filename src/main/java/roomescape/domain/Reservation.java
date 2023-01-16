package roomescape.domain;

import roomescape.dto.ReservationDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    public Reservation(ReservationDto reservationDto) {
        this.date = LocalDate.parse(reservationDto.getDate(), DateTimeFormatter.ISO_DATE);
        this.time = LocalTime.parse(reservationDto.getTime());
        this.name = reservationDto.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
