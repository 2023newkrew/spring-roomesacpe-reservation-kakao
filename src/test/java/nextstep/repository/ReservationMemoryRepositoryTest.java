package nextstep.repository;

import nextstep.Reservation;
import nextstep.Theme;
import nextstep.service.RoomEscapeService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;


class ReservationMemoryRepositoryTest {

    private final ReservationMemoryRepository repository = new ReservationMemoryRepository();

    private Reservation getReservationToAdd() {
        return Reservation.of(
                LocalDate.parse("2022-08-11"),
                LocalTime.parse("13:00:00"),
                "jin",
                Theme.DEFAULT_THEME
        );
    }

    @Test
    public void should_createAndIncreaseIndex_when_addRequestGiven() {
        Reservation reservation = getReservationToAdd();

        Reservation addedReservation = repository.add(reservation);

        Reservation expected = Reservation.of(
                0L,
                LocalDate.parse("2022-08-11"),
                LocalTime.parse("13:00:00"),
                "jin",
                Theme.DEFAULT_THEME
        );
        assertThat(addedReservation).isEqualTo(expected);
    }

    @Test
    public void should_findRightReservation_when_findRequestGiven() {
        repository.add(getReservationToAdd());

        Reservation addedReservation = repository.get(0L);

        Reservation expected = Reservation.of(
                0L,
                LocalDate.parse("2022-08-11"),
                LocalTime.parse("13:00:00"),
                "jin",
                Theme.DEFAULT_THEME
        );
        assertThat(addedReservation).isEqualTo(expected);
    }

    @Test
    public void should_deleteReservation_when_deleteRequestGiven() {
        repository.add(getReservationToAdd());

        repository.delete(0L);

        assertThat(repository.get(0L)).isNull();
    }
}