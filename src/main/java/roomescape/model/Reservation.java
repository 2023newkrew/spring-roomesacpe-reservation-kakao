package roomescape.model;

import roomescape.dto.ReservationRequestDto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private String theme;

    public Reservation(Long id, LocalDate date, LocalTime time, String name, String theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    public Reservation(ReservationRequestDto reservationRequestDto) {
        this(null, reservationRequestDto.getDate(), reservationRequestDto.getTime(), reservationRequestDto.getName(), "워너고홈");
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

    public void setId(Long id) {
        this.id = id;
    }
}
