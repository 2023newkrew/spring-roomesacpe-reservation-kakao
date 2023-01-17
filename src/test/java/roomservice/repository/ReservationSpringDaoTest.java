package roomservice.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import roomservice.domain.entity.Reservation;
import roomservice.domain.entity.Theme;
import roomservice.exceptions.exception.DuplicatedReservationException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
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
        testReservation = new Reservation(null, testDate, testTime, "daniel", 1L);
    }

    @Test
    void createTest() {
        long givenId = reservationDao.insertReservation(testReservation);
        assertThat(reservationDao.selectReservation(givenId).getTime()).isEqualTo(testTime);
        assertThat(reservationDao.selectReservation(givenId).getDate()).isEqualTo(testDate);
        assertThat(reservationDao.selectReservation(givenId).getName()).isEqualTo("daniel");

    }

    @Test
    void throwExceptionWhenDuplicated() {
        reservationDao.insertReservation(testReservation);
        assertThatThrownBy(() -> {
            reservationDao.insertReservation(testReservation);
        }).isInstanceOf(DuplicatedReservationException.class);
    }

    @Test
    void selectTest() {
        assertThat(reservationDao.selectReservation(1L).getName()).isEqualTo("A");
    }

    @Test
    void deleteTest() {
        assertThatCode(() -> {
            reservationDao.deleteReservation(1L);
        }).doesNotThrowAnyException();
    }
}
