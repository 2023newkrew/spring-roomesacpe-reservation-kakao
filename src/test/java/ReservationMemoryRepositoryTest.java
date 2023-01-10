import nextstep.Reservation;
import nextstep.Theme;
import nextstep.repository.ReservationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

class ReservationMemoryRepositoryTest {

    ReservationRepository repository;
    Theme testTheme;

    @BeforeEach
    void setUp() {
        repository = new ReservationMemoryRepository();
        testTheme = new Theme("Theme", "Theme desc", 10_000);
    }

    @DisplayName("예약을 저장한다.")
    @Test
    void save() {
        Reservation inputReservation = generateReservation(
                null, "2023-01-01", "13:00", "kim", testTheme);
        Reservation savedReservation = repository.save(inputReservation);

        Reservation expected = generateReservation(
                1L, "2023-01-01", "13:00", "kim", testTheme);

        Assertions.assertThat(savedReservation).isEqualTo(expected);
    }


    private Reservation generateReservation(Long id, String date, String time, String name, Theme theme) {
        return new Reservation(
                id,
                LocalDate.parse(date),
                LocalTime.parse(time),
                name,
                theme
        );
    }
}
