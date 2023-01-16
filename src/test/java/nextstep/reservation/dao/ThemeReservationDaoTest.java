package nextstep.reservation.dao;

import nextstep.RoomEscapeWebApplication;
import nextstep.reservation.ThemeReservationMock;
import nextstep.reservation.entity.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = RoomEscapeWebApplication.class)
@Transactional
class ThemeReservationDaoTest {

    private final static Long EXIST_THEME_ID = 1L;
    private final static Long NOT_EXIST_THEME_ID = Long.MAX_VALUE;
    private final static Long NOT_EXIST_RESERVATION_ID = Long.MAX_VALUE;

    @Autowired
    private ThemeReservationDao themeReservationDao;

    @Test
    @DisplayName("방탈출 예약하기")
    void test1(){
        Reservation reservation = ThemeReservationMock.makeRandomReservation(EXIST_THEME_ID);

        themeReservationDao.insert(reservation);
        Long reservationId = reservation.getId();
        Optional<Reservation> findReservation = themeReservationDao.findById(reservationId);

        assertThat(reservationId).isNotNull();
        assertThat(findReservation).isPresent();
    }

    @Test
    @DisplayName("이미 예약된 방탈출 예약을 취소한다.")
    void test2(){
        Reservation reservation = ThemeReservationMock.makeRandomReservation(EXIST_THEME_ID);
        themeReservationDao.insert(reservation);

        themeReservationDao.delete(reservation.getId());
        assertThat(themeReservationDao.findById(reservation.getId())).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 예약을 취소할 수 없다.")
    void test3(){
        assertThat(themeReservationDao.delete(NOT_EXIST_RESERVATION_ID)).isZero();
    }

    @Test
    @DisplayName("예약되지 않은 방을 조회한다.")
    void test5(){
        Optional<Reservation> findReservation = themeReservationDao.findById(1000000L);

        assertThat(findReservation).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 테마는 예약 할 수 없다.")
    void test6(){
        Reservation reservation = ThemeReservationMock.makeRandomReservation(NOT_EXIST_THEME_ID);
        assertThatThrownBy(() -> themeReservationDao.insert(reservation)).isInstanceOf(RuntimeException.class); // DataIntegrityViolationException
    }

    @Test
    @DisplayName("날짜와 시간이 같은 예약은 할 수 없다.")
    void test7(){
        Reservation reservation1 = ThemeReservationMock.makeRandomReservation(EXIST_THEME_ID);
        themeReservationDao.insert(reservation1);
        assertThatThrownBy(() -> themeReservationDao.insert(reservation1))
                .isInstanceOf(RuntimeException.class); // DuplicateKeyException
    }
}
