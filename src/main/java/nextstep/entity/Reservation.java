package nextstep.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Reservation {

    private Long id;

    private final LocalDate date;

    private final LocalTime time;

    private final String name;

    private final Theme theme;

    @Builder
    public Reservation(LocalDate date, LocalTime time, String name, Theme theme) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    public static Reservation creteReservation(Reservation other, Long id) {
        Reservation reservation = Reservation.builder()
                .date(other.getDate())
                .time(other.getTime())
                .theme(other.getTheme())
                .name(other.name)
                .build();
        reservation.id = id;
        return reservation;
    }

}
