package reservation.dto.response;

import reservation.domain.Reservation;
import reservation.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationResponse {
    private final long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final ThemeResponse theme;

    public ReservationResponse(long id, LocalDate date, LocalTime time, String name, ThemeResponse theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    public ReservationResponse(Reservation reservation, Theme theme) {
        this.id = reservation.getId();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
        this.name = reservation.getName();
        this.theme = new ThemeResponse(theme.getId(), theme.getName(), theme.getDesc(), theme.getPrice());
    }

    public long getId() {
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

    public ThemeResponse getTheme() {
        return theme;
    }
}
