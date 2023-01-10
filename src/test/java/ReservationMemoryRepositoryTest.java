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
    Reservation testReservation;
    Theme testTheme;

    @BeforeEach
    void setUp() {
        repository = new ReservationMemoryRepository();
        testTheme = new Theme("Theme", "Theme desc", 10_000);
        testReservation = new Reservation(
                null,
                LocalDate.parse("2022-01-01"),
                LocalTime.parse("13:00"),
                "kim",
                testTheme);
    }

    @DisplayName("예약을 저장한다.")
    @Test
    void save() {
        Reservation savedReservation = repository.save(testReservation);

        Reservation expected = generateReservation(
                1L, "2022-01-01", "13:00", "kim", testTheme);

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
