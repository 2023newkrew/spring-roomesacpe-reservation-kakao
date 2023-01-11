package roomservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomservice.domain.Reservation;
import roomservice.exceptions.exception.DuplicatedReservationException;
import roomservice.exceptions.exception.NonExistentReservationException;
import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE)
public class ReservationDaoTest {
    private final static LocalDate testDate = LocalDate.of(2023, 1, 1);
    private final static LocalTime testTime = LocalTime.of(13, 0);

//    @Autowired
//    private JdbcTemplate jdbcTemplate;
    private ReservationDao reservationDao;
    private Reservation testReservation;

    @BeforeEach
    void setUp() {
        reservationDao = new ReservationDao();
        testReservation = new Reservation();
        testReservation.setDate(testDate);
        testReservation.setTime(testTime);
        testReservation.setName("daniel");
    }

    @Test
    void createTest() {
        assertThat(reservationDao.insertReservation(testReservation)).isEqualTo(1L);
    }

    @Test
    void throwExceptionWhenDuplicated() {
        reservationDao.insertReservation(testReservation);
        assertThatThrownBy(() -> {
            reservationDao.insertReservation(testReservation);
        }).isInstanceOf(DuplicatedReservationException.class);
    }

    @Test
    void showTest() {
        reservationDao.insertReservation(testReservation);
        testReservation.setId(1L);
        assertThat(reservationDao.selectReservation(1L)).isEqualTo(testReservation);
    }

    @Test
    void throwExceptionWhenReservationNotExist() {
        assertThatThrownBy(() -> {
            reservationDao.selectReservation(1L);
        }).isInstanceOf(NonExistentReservationException.class);

        assertThatThrownBy(() -> {
            reservationDao.deleteReservation(1L);
        }).isInstanceOf(NonExistentReservationException.class);
    }

    @Test
    void deleteTest() {
        reservationDao.insertReservation(testReservation);
        testReservation.setId(1L);
        reservationDao.deleteReservation(1L);

        assertThatThrownBy(() -> {
            reservationDao.selectReservation(1L);
        }).isInstanceOf(NonExistentReservationException.class);
    }
}
