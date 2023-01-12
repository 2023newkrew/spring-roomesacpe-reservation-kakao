package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.temporal.ChronoUnit;
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
    private final static LocalDate testDate = LocalDate.now();
    private final static LocalTime testTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ReservationDao reservationDao;
    private Reservation testReservation;

    @BeforeEach
    void setUp() {
        reservationDao = new ReservationSpringDao(jdbcTemplate);
        testReservation = Reservation.builder().date(testDate).time(testTime).name("daniel").build();
    }

    @Test
    void createTest() {
        assertThat(reservationDao.add(testReservation)).isPositive();
    }

    @Test
    void throwExceptionWhenDuplicated() {
        testReservation.setTime(testReservation.getTime().plusHours(1));
        reservationDao.add(testReservation);
        assertThatThrownBy(() -> reservationDao.add(testReservation)).isInstanceOf(DuplicatedReservationException.class);
    }

    @Test
    void showTest() {
        testReservation.setTime(testReservation.getTime().plusHours(2));
        long id = reservationDao.add(testReservation);
        testReservation.setId(id);
        assertThat(reservationDao.findById(id)).isEqualTo(testReservation);
    }

    @Test
    void throwExceptionWhenReservationNotExist() {
        assertThatThrownBy(() -> reservationDao.findById(0L)).isInstanceOf(NoSuchReservationException.class);
        assertThatThrownBy(() -> reservationDao.deleteById(0L)).isInstanceOf(NoSuchReservationException.class);
    }

    @Test
    void deleteTest() {
        testReservation.setTime(testReservation.getTime().plusHours(3));
        long id = reservationDao.add(testReservation);
        reservationDao.deleteById(id);
        assertThatThrownBy(() -> reservationDao.findById(id)).isInstanceOf(NoSuchReservationException.class);
    }
}
