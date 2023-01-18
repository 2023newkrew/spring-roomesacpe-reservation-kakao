package roomescape.reservation.domain;

import roomescape.theme.domain.Theme;
import roomescape.reservation.dto.ReservationDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Long themeId;

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Long themeId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
    }

    public Reservation(ReservationDto reservationDto) {
        this.date = LocalDate.parse(reservationDto.getDate(), DateTimeFormatter.ISO_DATE);
        this.time = LocalTime.parse(reservationDto.getTime());
        this.name = reservationDto.getName();
        this.themeId = reservationDto.getThemeId();
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

    public Long getThemeId() {
        return themeId;
    }

}
