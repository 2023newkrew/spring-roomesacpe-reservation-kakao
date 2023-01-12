package roomescape.model;

import roomescape.dto.ReservationRequestDto;

import java.time.LocalDate;
import java.time.LocalTime;


public class Reservation {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Long themeId;

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Long themeId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
    }

    public Reservation(ReservationRequestDto req) {
        this(null, req.getDate(), req.getTime(), req.getName(), req.getThemeId());  //
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

    public Long getThemeId() {
        return themeId;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
