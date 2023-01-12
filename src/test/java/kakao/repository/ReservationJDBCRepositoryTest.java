package kakao.repository;

import domain.Reservation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
public class ReservationJDBCRepositoryTest {
    @Autowired
    private ReservationJDBCRepository reservationJDBCRepository;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Reservation reservation = Reservation.builder()
            .name("baker")
            .date(LocalDate.of(2022, 10, 13))
            .time(LocalTime.of(13, 00))
            .theme(ThemeRepository.theme)
            .build();

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.execute("TRUNCATE TABLE theme");
        jdbcTemplate.execute("ALTER TABLE theme ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.update("insert into theme(name, desc, price)\nvalues ('워너고홈', '병맛 어드벤처 회사 코믹물', 29000)");
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");

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

    @DisplayName("저장된 id에 해당하는 데이터를 삭제하고 삭제된 개수를 반환한다")
    @Test
    void delete() {
        reservationJDBCRepository.save(reservation);

        Assertions.assertThat(reservationJDBCRepository.delete(1L)).isOne();
        Assertions.assertThat(reservationJDBCRepository.delete(1L)).isZero();
    }
}
