package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.entity.Reservation;
import roomescape.exceptions.exception.DuplicatedReservationException;
import roomescape.exceptions.exception.NoSuchReservationException;

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
    void addTest() {
        assertThat(reservationDao.add(testReservation)).isEqualTo(1L);
    }

    @Test
    void throwExceptionDuplicated() {
        reservationDao.add(testReservation);
        assertThatThrownBy(() -> reservationDao.add(testReservation)).isInstanceOf(DuplicatedReservationException.class);
    }

    @Test
    void findTest() {
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
