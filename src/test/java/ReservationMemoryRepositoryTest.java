import nextstep.Reservation;
import nextstep.Theme;
import nextstep.repository.ReservationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

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


    @DisplayName("여러개의 예약을 연속적으로 저장한다.")
    @Test
    void save_multi() {
        Reservation inputReservation1 = generateReservation(
                null, "2023-01-01", "13:00", "kim", testTheme);
        Reservation inputReservation2 = generateReservation(
                null, "2023-01-02", "14:00", "lee", testTheme);
        Reservation inputReservation3 = generateReservation(
                null, "2023-01-03", "15:00", "park", testTheme);

        Reservation savedReservation1 = repository.save(inputReservation1);
        Reservation savedReservation2 = repository.save(inputReservation2);
        Reservation savedReservation3 = repository.save(inputReservation3);

        Reservation expected1 = generateReservation(
                1L, "2023-01-01", "13:00", "kim", testTheme);
        Reservation expected2 = generateReservation(
                2L, "2023-01-02", "14:00", "lee", testTheme);
        Reservation expected3 = generateReservation(
                3L, "2023-01-03", "15:00", "park", testTheme);

        Assertions.assertThat(savedReservation1).isEqualTo(expected1);
        Assertions.assertThat(savedReservation2).isEqualTo(expected2);
        Assertions.assertThat(savedReservation3).isEqualTo(expected3);
    }

    @DisplayName("id로 예약을 조회한다 - 조회 성공")
    @Test
    void find_success() {
        Reservation inputReservation1 = generateReservation(
                null, "2023-01-01", "13:00", "kim", testTheme);
        Reservation inputReservation2 = generateReservation(
                null, "2023-01-02", "14:00", "lee", testTheme);
        Reservation inputReservation3 = generateReservation(
                null, "2023-01-03", "15:00", "park", testTheme);

        repository.save(inputReservation1);
        repository.save(inputReservation2);
        repository.save(inputReservation3);

        Optional<Reservation> result1 = repository.findById(1L);
        Optional<Reservation> result2 = repository.findById(2L);
        Optional<Reservation> result3 = repository.findById(3L);


        Reservation expected1 = generateReservation(
                1L, "2023-01-01", "13:00", "kim", testTheme);
        Reservation expected2 = generateReservation(
                2L, "2023-01-02", "14:00", "lee", testTheme);
        Reservation expected3 = generateReservation(
                3L, "2023-01-03", "15:00", "park", testTheme);

        Assertions.assertThat(result1).isNotEmpty()
                .get().isEqualTo(expected1);
        Assertions.assertThat(result2).isNotEmpty()
                .get().isEqualTo(expected2);
        Assertions.assertThat(result3).isNotEmpty()
                .get().isEqualTo(expected3);
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
