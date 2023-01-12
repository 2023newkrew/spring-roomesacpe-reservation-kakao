package nextstep.domain.reservation;

import nextstep.domain.dto.CreateReservationDto;
import nextstep.domain.theme.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Theme theme;

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
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

    @Override
    public boolean equals(Object obj) {
        Reservation reservation = (Reservation) obj;
        return this.name.equals(reservation.getName())
        && this.date.equals(reservation.getDate())
        && this.time.equals(reservation.getTime())
        && this.theme.equals(reservation.getTheme());
    }

    public static Reservation createReservation(CreateReservationDto createReservationDto) {
        return new Reservation(
                null,
                LocalDate.parse(createReservationDto.getLocalDate()),
                LocalTime.parse(createReservationDto.getLocalTime()),
                createReservationDto.getName(),
                new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000)
        );
    }
}
