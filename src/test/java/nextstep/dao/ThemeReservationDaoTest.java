package nextstep.dao;

import nextstep.RoomEscapeWebApplication;
import nextstep.reservation.dao.ThemeReservationDao;
import nextstep.reservation.dto.ReservationDto;
import nextstep.reservation.entity.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = RoomEscapeWebApplication.class)
class ThemeReservationDaoTest {

    private final static Long EXIST_THEME_ID = 1L;
    private final static Long NOT_EXIST_THEME_ID = Long.MAX_VALUE;
    private final static Long NOT_EXIST_RESERVATION_ID = Long.MAX_VALUE;
    public static final String RESERVATION_DATE = "2022-12-12";

    @Autowired
    private ThemeReservationDao themeReservationDao;

    @Test
    @DisplayName("방탈출 예약하기")
    void test1(){
        Reservation reservation = makeRandomReservation(RESERVATION_DATE, "14:31", EXIST_THEME_ID);

        themeReservationDao.insert(reservation);
        Long reservationId = reservation.getId();
        Optional<Reservation> findReservation = themeReservationDao.findById(reservationId);

        assertThat(reservationId).isNotNull();
        assertThat(findReservation).isPresent();
    }

    @Test
    @DisplayName("이미 예약된 방탈출 예약을 취소한다.")
    void test2(){
        Reservation reservation = makeRandomReservation(RESERVATION_DATE, "14:02", EXIST_THEME_ID);
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
        Reservation reservation = makeRandomReservation(RESERVATION_DATE, "14:06", NOT_EXIST_THEME_ID);
        assertThatThrownBy(() -> themeReservationDao.insert(reservation)).isInstanceOf(RuntimeException.class); // DataIntegrityViolationException
    }

    @Test
    @DisplayName("날짜와 시간이 같은 예약은 할 수 없다.")
    void test7(){
        Reservation reservation1 = makeRandomReservation(RESERVATION_DATE, "14:07", EXIST_THEME_ID);
        Reservation reservation2 = makeRandomReservation(RESERVATION_DATE, "14:07", EXIST_THEME_ID);
        themeReservationDao.insert(reservation1);
        assertThatThrownBy(() -> themeReservationDao.insert(reservation2))
                .isInstanceOf(RuntimeException.class); // DuplicateKeyException
    }

    Reservation makeRandomReservation(String date, String time, Long themeId){
        List<String> names = List.of("omin", "ethan", "java");

        int index = ThreadLocalRandom.current().nextInt(3);

        return new ReservationDto(LocalDate.parse(date), LocalTime.parse(time), names.get(index), themeId).toEntity();
    }
}
