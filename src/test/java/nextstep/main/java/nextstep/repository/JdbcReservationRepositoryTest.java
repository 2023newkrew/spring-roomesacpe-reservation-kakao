package nextstep.main.java.nextstep.repository;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.domain.Theme;
import org.junit.jupiter.api.BeforeAll;
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
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationRepository repository;

    private static Theme defaultTheme;

    @BeforeAll
    static void beforeAll() {
        defaultTheme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
    }

    @BeforeEach
    void setUp() {
        repository = new JdbcReservationRepository(jdbcTemplate);
    }

    @DisplayName("예약 생성 기능 테스트")
    @Test
    public void saveTest() {
        Reservation expectedReservation = new Reservation(1L, LocalDate.of(2023, 1, 9), LocalTime.of(1, 30), "name", defaultTheme);

        Reservation actualReservation = repository.save(expectedReservation);

        assertThat(actualReservation)
                .isEqualTo(expectedReservation);
    }

    @DisplayName("예약 단건 조회 기능 테스트")
    @Test
    public void findOneTest() {
        Reservation reservation = new Reservation(1L, LocalDate.of(2023, 1, 9), LocalTime.of(1, 30), "name", defaultTheme);
        repository.save(reservation);

        assertThat(repository.findOne(1L)
                .get())
                .isEqualTo(reservation);

        assertThat(repository.findOne(2L)
                .isEmpty())
                .isTrue();
    }

    @DisplayName("예약 단건 삭제 기능 테스트")
    @Test
    public void deleteOneTest() {
        Reservation reservation = new Reservation(1L, LocalDate.of(2023, 1, 9), LocalTime.of(1, 30), "name", defaultTheme);
        repository.save(reservation);

        assertThat(repository.findOne(1L)
                .get())
                .isEqualTo(reservation);
        repository.deleteOne(1L);
        assertThat(repository.findOne(1L)
                .isEmpty())
                .isTrue();
    }

    @DisplayName("동일 날짜와 시간을 가진 예약 존재 확인 메서드 테스트")
    @Test
    public void existByDateAndTimeTest() {
        LocalDate localDate = LocalDate.of(2023, 1, 9);
        LocalTime localTime = LocalTime.of(1, 30);
        Reservation reservation = new Reservation(1L, LocalDate.of(2023, 1, 9), localTime, "name", defaultTheme);

        assertThat(repository.existsByDateAndTime(localDate, localTime)).isFalse();
        repository.save(reservation);
        assertThat(repository.existsByDateAndTime(localDate, localTime)).isTrue();
    }
}
