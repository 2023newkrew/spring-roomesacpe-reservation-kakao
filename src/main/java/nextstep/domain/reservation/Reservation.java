package nextstep.domain.reservation;

import nextstep.domain.dto.reservation.CreateReservationDto;
import nextstep.domain.theme.Theme;
import nextstep.exception.IllegalReservationTimeException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Reservation {
    private static final Set POSSIBLE_SECOND_SET = new HashSet(List.of(0));
    private static final Set POSSIBLE_MINUTE_SET = new HashSet(List.of(0,30));

    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Theme theme;

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        checkReservationTime(time);
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    private void checkReservationTime(LocalTime time) {
        if (!POSSIBLE_SECOND_SET.contains(time.getSecond())) {
            throw new IllegalReservationTimeException();
        }
        if (!POSSIBLE_MINUTE_SET.contains(time.getMinute())) {
            throw new IllegalReservationTimeException();
        }
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

    public static Reservation createReservation(CreateReservationDto createReservationDto) {
        return new Reservation(
                null,
                LocalDate.parse(createReservationDto.getDate().toString()),
                LocalTime.parse(createReservationDto.getTime().toString()),
                createReservationDto.getName(),
                new Theme(createReservationDto.getThemeId(), "", "", 0)
        );
    }

    @Override
    public boolean equals(Object obj) {
        Reservation reservation = (Reservation) obj;
        return this.name.equals(reservation.getName())
                && this.date.equals(reservation.getDate())
                && this.time.equals(reservation.getTime())
                && this.theme.equals(reservation.getTheme());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, time, theme);
    }
}
