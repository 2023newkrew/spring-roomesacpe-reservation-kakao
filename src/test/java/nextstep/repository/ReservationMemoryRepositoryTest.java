package nextstep.repository;

import nextstep.Reservation;
import nextstep.service.RoomEscapeService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;


class ReservationMemoryRepositoryTest {

    private final ReservationMemoryRepository repository = new ReservationMemoryRepository();
    @Test
    public void should_createAndIncreaseIndex_when_addRequestGiven() {
        Reservation reservation = new Reservation(
                LocalDate.parse("2022-08-11"),
                LocalTime.parse("13:00:00"),
                "jin",
                RoomEscapeService.DEFAULT_THEME
        );

        Reservation addedReservation = repository.add(reservation);

        Reservation expected = new Reservation(
                0L,
                LocalDate.parse("2022-08-11"),
                LocalTime.parse("13:00:00"),
                "jin",
                RoomEscapeService.DEFAULT_THEME
        );
        assertThat(addedReservation).isEqualTo(expected);
    }
}