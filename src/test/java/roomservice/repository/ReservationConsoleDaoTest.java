package roomservice.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomservice.domain.Reservation;
import roomservice.exceptions.exception.DuplicatedReservationException;
import roomservice.exceptions.exception.NonExistentReservationException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReservationConsoleDaoTest {
    private final static LocalDate testDate = LocalDate.of(2023, 1, 1);
    private final static LocalTime testTime = LocalTime.of(13, 0);

    private ReservationDao reservationDao;
    private Reservation testReservation;

    @BeforeEach
    void setUp() {
        reservationDao = new ReservationConsoleDao();
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
        long id = reservationDao.insertReservation(testReservation);
        testReservation.setId(id);
        assertThat(reservationDao.selectReservation(id)).isEqualTo(testReservation);
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
        long id = reservationDao.insertReservation(testReservation);
        testReservation.setId(id);
        reservationDao.deleteReservation(id);

        assertThatThrownBy(() -> {
            reservationDao.selectReservation(id);
        }).isInstanceOf(NonExistentReservationException.class);
    }
}
