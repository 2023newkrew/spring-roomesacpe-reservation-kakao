package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(listeners = RepositoryTestExecutionListener.class,
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
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
                null, "2023-01-01", "13:00", "kim", testTheme);
        expectedReservation2 = generateReservation(
                null, "2023-01-02", "14:00", "lee", testTheme);
        expectedReservation3 = generateReservation(
                null, "2023-01-03", "15:00", "park", testTheme);

    }

    @DisplayName("예약을 저장한다.")
    @Test
    void save() {
        Reservation savedReservation = repository.save(inputReservation1);

        expectedReservation1.setId(savedReservation.getId());

        assertThat(savedReservation).isEqualTo(expectedReservation1);
    }

    @DisplayName("여러개의 예약을 연속적으로 저장한다.")
    @Test
    void save_multi() {
        Reservation savedReservation1 = repository.save(inputReservation1);
        Reservation savedReservation2 = repository.save(inputReservation2);
        Reservation savedReservation3 = repository.save(inputReservation3);

        expectedReservation1.setId(savedReservation1.getId());
        expectedReservation2.setId(savedReservation2.getId());
        expectedReservation3.setId(savedReservation3.getId());

        assertThat(savedReservation1).isEqualTo(expectedReservation1);
        assertThat(savedReservation2).isEqualTo(expectedReservation2);
        assertThat(savedReservation3).isEqualTo(expectedReservation3);
    }

    @DisplayName("id로 예약을 조회한다 - 조회 성공")
    @Test
    void find_success() {
        Reservation savedReservation1 = repository.save(inputReservation1);
        Reservation savedReservation2 = repository.save(inputReservation2);
        Reservation savedReservation3 = repository.save(inputReservation3);

        expectedReservation1.setId(savedReservation1.getId());
        expectedReservation2.setId(savedReservation2.getId());
        expectedReservation3.setId(savedReservation3.getId());

        Optional<Reservation> result1 = repository.findById(savedReservation1.getId());
        Optional<Reservation> result2 = repository.findById(savedReservation2.getId());
        Optional<Reservation> result3 = repository.findById(savedReservation3.getId());

        assertThat(result1).isNotEmpty()
                .get().isEqualTo(expectedReservation1);
        assertThat(result2).isNotEmpty()
                .get().isEqualTo(expectedReservation2);
        assertThat(result3).isNotEmpty()
                .get().isEqualTo(expectedReservation3);
    }

    @DisplayName("id로 예약을 조회한다 - 조회 실패")
    @Test
    void find_fail() {
        Optional<Reservation> result = repository.findById(1L);
        assertThat(result).isEmpty();
    }

    @DisplayName("모든 예약을 조회한다")
    @Test
    void findAll() {
        Reservation savedReservation1 = repository.save(inputReservation1);
        Reservation savedReservation2 = repository.save(inputReservation2);
        Reservation savedReservation3 = repository.save(inputReservation3);

        expectedReservation1.setId(savedReservation1.getId());
        expectedReservation2.setId(savedReservation2.getId());
        expectedReservation3.setId(savedReservation3.getId());

        List<Reservation> result = repository.findAll();

        assertThat(result).contains(expectedReservation1, expectedReservation2, expectedReservation3);
    }

    @DisplayName("모든 예약을 조회한다 - 예약이 존재하지 않을 경우")
    @Test
    void findAll_empty() {
        List<Reservation> result = repository.findAll();
        assertThat(result).isEmpty();
    }

    @DisplayName("예약을 삭제한다 - 삭제 성공")
    @Test
    void delete_success() {
        Reservation savedReservation1 = repository.save(inputReservation1);

        boolean result = repository.delete(savedReservation1.getId());

        assertThat(result).isTrue();
        assertThat(repository.findById(savedReservation1.getId())).isEmpty();
    }

    @DisplayName("예약을 삭제한다 - 삭제할 대상 없음")
    @Test
    void delete_fail() {
        boolean result = repository.delete(1L);
        assertThat(result).isFalse();
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