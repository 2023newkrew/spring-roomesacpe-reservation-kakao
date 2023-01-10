package kakao.repository;

import kakao.domain.Reservation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
public class ReservationJDBCRepositoryTest {
    @Autowired
    private ReservationJDBCRepository reservationJDBCRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final Reservation reservation = new Reservation(
            LocalDate.of(2022, 10, 13),
            LocalTime.of(13, 00),
            "baker",
            ThemeRepository.theme
    );

    @Transactional
    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE reservation");
        jdbcTemplate.execute("ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1");
    }

    @DisplayName("데이터베이스에 reservation을 저장한다")
    @Test
    void createReservation() {
        long id = reservationJDBCRepository.save(reservation);

        Assertions.assertThat(id).isOne();
    }

    @DisplayName("데이터베이스에서 저장된 reservation을 id로 조회한다")
    @Test
    void findById() {
        reservationJDBCRepository.save(reservation);
        Reservation cp = reservationJDBCRepository.findById(1L);

        Assertions.assertThat(reservation.getName()).isEqualTo(cp.getName());
        Assertions.assertThat(reservation.getDate()).isEqualTo(cp.getDate());
        Assertions.assertThat(reservation.getTime()).isEqualTo(cp.getTime());
        Assertions.assertThat(reservation.getTheme()).isEqualTo(cp.getTheme());
    }

    @DisplayName("저장된 날짜에 해당하는 데이터들을 조회한다")
    @Test
    void findByDateAndTime() {
        reservationJDBCRepository.save(reservation);
        List<Reservation> savedReservations =
                reservationJDBCRepository.findByDateAndTime(
                        LocalDate.of(2022, 10, 13),
                        LocalTime.of(13, 00)
                );

        Assertions.assertThat(savedReservations.size()).isOne();
    }

    @DisplayName("저장된 id에 해당하는 데이터를 삭제한다, 삭제되면 1, 그렇지 않으면 0을 반환한다")
    @Test
    void delete() {
        reservationJDBCRepository.save(reservation);

        Assertions.assertThat(reservationJDBCRepository.delete(2L)).isZero();
        Assertions.assertThat(reservationJDBCRepository.delete(1L)).isOne();
    }
}
