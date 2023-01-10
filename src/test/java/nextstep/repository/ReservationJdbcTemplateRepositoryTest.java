package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("classpath:tableInit.sql")
class ReservationJdbcTemplateRepositoryTest {

    @Autowired
    ReservationRepository repository;
    Theme testTheme;

    Reservation inputReservation1;
    Reservation inputReservation2;
    Reservation inputReservation3;

    Reservation expectedReservation1;
    Reservation expectedReservation2;
    Reservation expectedReservation3;

    @BeforeEach
    void setUp() {
        testTheme = new Theme("Theme", "Theme desc", 10_000);

        inputReservation1 = generateReservation(
                null, "2023-01-01", "13:00", "kim", testTheme);
        inputReservation2 = generateReservation(
                null, "2023-01-02", "14:00", "lee", testTheme);
        inputReservation3 = generateReservation(
                null, "2023-01-03", "15:00", "park", testTheme);

        expectedReservation1 = generateReservation(
                1L, "2023-01-01", "13:00", "kim", testTheme);
        expectedReservation2 = generateReservation(
                2L, "2023-01-02", "14:00", "lee", testTheme);
        expectedReservation3 = generateReservation(
                3L, "2023-01-03", "15:00", "park", testTheme);

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

    @DisplayName("예약을 저장한다.")
    @Test
    void save() {
        Reservation savedReservation = repository.save(inputReservation1);
        assertThat(savedReservation).isEqualTo(expectedReservation1);
    }

    @DisplayName("여러개의 예약을 연속적으로 저장한다.")
    @Test
    void save_multi() {
        Reservation savedReservation1 = repository.save(inputReservation1);
        Reservation savedReservation2 = repository.save(inputReservation2);
        Reservation savedReservation3 = repository.save(inputReservation3);

        assertThat(savedReservation1).isEqualTo(expectedReservation1);
        assertThat(savedReservation2).isEqualTo(expectedReservation2);
        assertThat(savedReservation3).isEqualTo(expectedReservation3);
    }
}