package nextstep.main.java.nextstep.repository;

import nextstep.main.java.nextstep.domain.Reservation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class JdbcReservationRepositoryTest {
    private static final Long DEFAULT_THEME_ID = 1L;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ReservationRepository repository;

    @BeforeEach
    void setUp() {
        repository = new JdbcReservationRepository(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {

    }

    @DisplayName("예약 생성 기능 테스트")
    @Test
    public void saveTest() {
        Reservation expectedReservation = new Reservation(LocalDate.of(2023, 1, 9), LocalTime.of(1, 30), "name", DEFAULT_THEME_ID);

        Reservation actualReservation = repository.save(expectedReservation);

        assertThat(actualReservation.getDate()).isEqualTo(expectedReservation.getDate());
        assertThat(actualReservation.getTime()).isEqualTo(expectedReservation.getTime());
        assertThat(actualReservation.getName()).isEqualTo(expectedReservation.getName());
        assertThat(actualReservation.getThemeId()).isEqualTo(expectedReservation.getThemeId());
    }

    @DisplayName("예약 단건 조회 기능 테스트")
    @Test
    public void findOneTest() {
        Reservation savedReservation = repository.save(new Reservation(LocalDate.of(2023, 1, 9), LocalTime.of(1, 30), "name", DEFAULT_THEME_ID));

        assertThat(repository.findOne(savedReservation.getId())
                .get())
                .isEqualTo(savedReservation);

        Long nonExistReservationId = 0L;
        assertThat(repository.findOne(nonExistReservationId)
                .isEmpty())
                .isTrue();
    }

    @Test
    @DisplayName("테마 아이디로 예약 조회 테스트")
    void findAllByThemeIdTest() {
        repository.save(new Reservation(LocalDate.of(2023, 1, 9), LocalTime.of(1, 30), "reservation1", DEFAULT_THEME_ID));
        repository.save(new Reservation(LocalDate.of(2025, 1, 9), LocalTime.of(1, 30), "reservation2", DEFAULT_THEME_ID));
        assertThat(repository.findAllByThemeId(DEFAULT_THEME_ID)
                .size()).isEqualTo(2);
        assertThat(repository.findAllByThemeId(0L)
                .size()).isEqualTo(0);
    }

    @Test
    @DisplayName("예약 단건 삭제 기능 테스트")
    public void deleteOneTest() {
        Reservation savedReservation = repository.save(new Reservation(LocalDate.of(2023, 1, 9), LocalTime.of(1, 30), "name", DEFAULT_THEME_ID));
        Long savedReservationId = savedReservation.getId();
        assertThat(repository.findOne(savedReservationId)
                .get())
                .isEqualTo(savedReservation);

        assertThat(repository.deleteOne(savedReservationId)).isTrue();
        assertThat(repository.findOne(savedReservationId)
                .isEmpty())
                .isTrue();
    }

    @DisplayName("동일 날짜와 시간을 가진 예약 존재 확인 메서드 테스트")
    @Test
    public void existByDateAndTimeTest() {
        LocalDate localDate = LocalDate.of(2023, 1, 9);
        LocalTime localTime = LocalTime.of(1, 30);
        Reservation reservation = new Reservation(LocalDate.of(2023, 1, 9), localTime, "name", DEFAULT_THEME_ID);

        assertThat(repository.existsByDateAndTime(localDate, localTime)).isFalse();
        repository.save(reservation);
        assertThat(repository.existsByDateAndTime(localDate, localTime)).isTrue();
    }
}
