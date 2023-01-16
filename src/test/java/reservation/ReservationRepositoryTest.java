package reservation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import reservation.domain.Reservation;
import reservation.domain.Theme;
import reservation.respository.ReservationRepository;
import reservation.respository.ThemeRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Reservation Repository Test")
@JdbcTest
public class ReservationRepositoryTest {
    private ReservationRepository reservationRepository;
    private Theme theme;
    private Reservation reservation;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ReservationRepositoryTest() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        LocalTime time = LocalTime.of(11, 0);

        reservation = new Reservation(null, date, time, "name", null);
    }

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository(jdbcTemplate);

        ThemeRepository themeRepository = new ThemeRepository(jdbcTemplate);
        Theme that = new Theme(null, "워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
        Long themeId = themeRepository.createTheme(that);

        theme = new Theme(themeId, that.getName(), that.getDesc(), that.getPrice());
        reservation = new Reservation(null, reservation.getDate(), reservation.getTime(), reservation.getName(), theme.getId());
    }

    @AfterEach
    void setDown() {
        String[] sqls = {"DELETE FROM reservation", "DELETE FROM theme"};
        for (String sql : sqls) {
            jdbcTemplate.update(sql);
        }
    }

    @Test
    @DisplayName("예약 생성이 되어야 한다.")
    void save() {
        Long id = reservationRepository.createReservation(reservation);
        assertThat(id).isGreaterThan(0);
    }

    @Test
    @DisplayName("생성된 예약을 조회할 수 있어야 한다.")
    void find() {
        Long id = reservationRepository.createReservation(reservation);
        Reservation reservation = reservationRepository.getReservation(id);
        assertThat(reservation.getThemeId()).isEqualTo(theme.getId());
    }

    @Test
    @DisplayName("생성된 예약을 취소할 수 있어야 한다.")
    void delete() {
        Long id = reservationRepository.createReservation(reservation);
        int rowCount = reservationRepository.deleteReservation(id);
        assertThat(rowCount).isEqualTo(1);
    }

    @Test
    @DisplayName("시간과 날짜가 중복되는 예약은 불가능하다.")
    void duplicate() {
        Long id = reservationRepository.createReservation(reservation);
        assertThat(reservationRepository.existReservation(reservation.getDate(), reservation.getTime(), reservation.getThemeId()))
                .isTrue();
    }
}
