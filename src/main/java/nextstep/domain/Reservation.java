package nextstep.domain;

import nextstep.dto.CreateReservationRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Long themeId;



    public Reservation(Long id, Reservation reservation) {
        this(id, reservation.date, reservation.time, reservation.name, reservation.themeId);
    }

    public Reservation(LocalDate date, LocalTime time, String name, Long themeId) {
        this(null, date, time, name, themeId);
    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Long themeId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
    }

    public boolean isSameDateAndTime(LocalDate date, LocalTime time) {
        return this.date.equals(date) && this.time.equals(time);
    }

    public boolean isSameId(Long id) {
        return Objects.equals(this.id, id);
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

    public static Reservation from(CreateReservationRequest createReservationRequest) {
        return new Reservation(
                LocalDate.parse(createReservationRequest.getDate()),
                LocalTime.parse(createReservationRequest.getTime()),
                createReservationRequest.getName(),
                createReservationRequest.getThemeId()
        );
    }
}
