package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.entity.Reservation;
import roomescape.exceptions.exception.DuplicatedReservationException;
import roomescape.exceptions.exception.NoSuchReservationException;
import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE)
public class ReservationSpringDaoTest {
    private final static LocalDate testDate = LocalDate.of(2023, 1, 1);
    private final static LocalTime testTime = LocalTime.of(13, 0);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationDao reservationDao;
    private Reservation testReservation;

    @BeforeEach
    void setUp() {
        reservationDao = new ReservationSpringDao(jdbcTemplate);
        jdbcTemplate.execute("DROP TABLE RESERVATION IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE RESERVATION(" +
                "    id          bigint not null auto_increment,\n" +
                "    date        date,\n" +
                "    time        time,\n" +
                "    name        varchar(20),\n" +
                "    theme_name  varchar(20),\n" +
                "    theme_desc  varchar(255),\n" +
                "    theme_price int,\n" +
                "    primary key (id)\n)");
        testReservation = new Reservation();
        testReservation.setDate(testDate);
        testReservation.setTime(testTime);
        testReservation.setName("daniel");
    }

    @Test
    void createTest() {
        assertThat(reservationDao.add(testReservation)).isEqualTo(1L);
    }

    @Test
    void throwExceptionWhenDuplicated() {
        reservationDao.add(testReservation);
        assertThatThrownBy(() -> reservationDao.add(testReservation)).isInstanceOf(DuplicatedReservationException.class);
    }

    @Test
    void showTest() {
        long id = reservationDao.add(testReservation);
        testReservation.setId(id);
        assertThat(reservationDao.findById(id)).isEqualTo(testReservation);
    }

    @Test
    void throwExceptionWhenReservationNotExist() {
        assertThatThrownBy(() -> reservationDao.findById(1L)).isInstanceOf(NoSuchReservationException.class);

        assertThatThrownBy(() -> reservationDao.deleteById(1L)).isInstanceOf(NoSuchReservationException.class);
    }

    @Test
    void deleteTest() {
        long id = reservationDao.add(testReservation);
        testReservation.setId(id);
        reservationDao.deleteById(id);

        assertThatThrownBy(() -> reservationDao.findById(id)).isInstanceOf(NoSuchReservationException.class);
    }
}
