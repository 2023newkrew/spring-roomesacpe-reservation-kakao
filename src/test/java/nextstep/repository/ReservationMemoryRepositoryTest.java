package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.exception.ReservationNotFoundException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class ReservationMemoryRepositoryTest {

    private final ReservationMemoryRepository repository = new ReservationMemoryRepository();
    private final Theme DEFAULT_THEME = new Theme(null, "워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    private Reservation getReservationToAdd() {
        return new Reservation(
                LocalDate.parse("2022-08-11"),
                LocalTime.parse("13:00:00"),
                "jin",
                DEFAULT_THEME
        );
    }

    @Test
    public void should_createAndIncreaseIndex_when_addRequestGiven() {
        Reservation reservation = getReservationToAdd();

        Reservation addedReservation = repository.save(reservation);

        Reservation expected = new Reservation(
                1L,
                LocalDate.parse("2022-08-11"),
                LocalTime.parse("13:00:00"),
                "jin",
                DEFAULT_THEME
        );
        assertThat(addedReservation).isEqualTo(expected);
    }

    @Test
    public void should_findRightReservation_when_findRequestGiven() {
        repository.save(getReservationToAdd());

        Reservation addedReservation = repository.findById(1L).orElseThrow(ReservationNotFoundException::new);

        Reservation expected = new Reservation(
                1L,
                LocalDate.parse("2022-08-11"),
                LocalTime.parse("13:00:00"),
                "jin",
                DEFAULT_THEME
        );
        assertThat(addedReservation).isEqualTo(expected);
    }

    @Test
    public void should_deleteReservation_when_deleteRequestGiven() {
        repository.save(getReservationToAdd());

        repository.deleteById(1L);

        assertThatThrownBy(() -> repository.findById(1L))
                .isInstanceOf(ReservationNotFoundException.class);
    }
}