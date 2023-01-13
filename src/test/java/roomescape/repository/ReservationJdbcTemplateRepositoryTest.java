package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.exception.NoSuchReservationException;

@JdbcTest
public class ReservationJdbcTemplateRepositoryTest {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationJdbcTemplateRepositoryTest(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.reservationRepository = new ReservationJdbcTemplateRepository(jdbcTemplate,
                dataSource);
    }

    private static final String expectedName = "name";
    private static final LocalDate expectedDate = LocalDate.parse("2022-08-11");
    private static final LocalTime expectedTime = LocalTime.parse("13:00:00");
    private static final Long invalidId = -1L;
    private static final Long themeId = 1L;
    private Long reservationId;

    @BeforeEach
    void setUp() {
        final Reservation reservation = Reservation.builder().name(expectedName).date(expectedDate)
                .time(expectedTime).themeId(themeId).build();

        reservationId = reservationRepository.save(reservation);
    }

    @DisplayName("id로 예약을 불러온다")
    @Test
    void findById() {
        final Reservation result = reservationRepository.findById(reservationId)
                .orElseThrow(NoSuchReservationException::new);

        assertThat(result.getName()).isEqualTo(expectedName);
        assertThat(result.getDate()).isEqualTo(expectedDate);
        assertThat(result.getTime()).isEqualTo(expectedTime);
    }

    @DisplayName("id에 해당하는 예약이 없다면 empty 를 반환한다")
    @Test
    void cannotFindWithInvalidId() {
        assertThat(reservationRepository.findById(invalidId)).isEmpty();
    }

    @DisplayName("예약 일시 및 시간으로 예약을 검색한다")
    @Test
    void findByDateAndTime() {
        final Reservation result = reservationRepository.findByDateAndTime(expectedDate, expectedTime)
                .orElseThrow(NoSuchReservationException::new);

        assertThat(result.getName()).isEqualTo(expectedName);
        assertThat(result.getDate()).isEqualTo(expectedDate);
        assertThat(result.getTime()).isEqualTo(expectedTime);
    }

    @DisplayName("id로 예약을 취소한다")
    @Test
    void deleteById() {
        final Boolean deleted = reservationRepository.deleteById(reservationId);

        assertThat(deleted).isTrue();
        assertThat(reservationRepository.findById(reservationId)).isEmpty();
    }

    @DisplayName("id에 해당하는 예약이 없다면 false 를 반환한다")
    @Test
    void deleteWithInvalidId() {
        final boolean deleted = reservationRepository.deleteById(invalidId);

        assertThat(deleted).isFalse();
    }
}
