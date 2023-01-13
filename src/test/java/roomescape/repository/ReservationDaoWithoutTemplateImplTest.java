package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.entity.Reservation;
import roomescape.reservation.repository.dao.ReservationDao;
import roomescape.reservation.repository.dao.ReservationDaoWithoutTemplateImpl;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE)
public class ReservationDaoWithoutTemplateImplTest {
    private final static LocalDate testDate = LocalDate.now();
    private final static LocalTime testTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
    private final static Long testThemeId = 100L;

    private ReservationDao reservationDao;
    private Reservation testReservation;

    @BeforeEach
    void setUp() {
        reservationDao = new ReservationDaoWithoutTemplateImpl();
        testReservation = Reservation.builder().date(testDate).time(testTime).themeId(testThemeId).build();
    }

    @Test
    void createTest() {
        assertThat(reservationDao.save(testReservation)).isPositive();
    }

    @Test
    void throwExceptionWhenDuplicated() {
        testReservation.setTime(testReservation.getTime().plusHours(1));
        assertThat(reservationDao.isReservationDuplicated(testReservation)).isEqualTo(false);
        reservationDao.save(testReservation);
        assertThat(reservationDao.isReservationDuplicated(testReservation)).isEqualTo(true);
    }

    @Test
    void showTest() {
        testReservation.setTime(testReservation.getTime().plusHours(2));
        long id = reservationDao.save(testReservation);
        testReservation.setId(id);
        assertThat(reservationDao.findById(id)).hasValue(testReservation);
    }

    @Test
    void throwExceptionWhenReservationNotExist() {
        assertThat(reservationDao.findById(0L)).isEmpty();
    }

    @Test
    void deleteTest() {
        testReservation.setTime(testReservation.getTime().plusHours(3));
        long id = reservationDao.save(testReservation);
        assertThat(reservationDao.delete(id)).isEqualTo(1);
        assertThat(reservationDao.delete(id)).isEqualTo(0);
    }
}
