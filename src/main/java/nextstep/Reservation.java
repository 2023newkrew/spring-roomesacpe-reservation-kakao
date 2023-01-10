package nextstep;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    public Reservation(ReservationRequest reservationRequest) {
        this.date = LocalDate.parse(reservationRequest.getDate(), DateTimeFormatter.ISO_DATE);
        this.time = LocalTime.parse(reservationRequest.getTime());
        this.name = reservationRequest.getName();
    }

    public Reservation(ReservationRequest reservationRequest, Long id) {
        this(reservationRequest);
        this.id = id;
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

    public boolean overlap(Reservation other) {
        return this.date.equals(other.date) && this.time.equals(other.time);
    }
}
