package dao;

import nextstep.RoomEscapeWebApplication;
import nextstep.entity.Reservation;
import nextstep.dao.ThemeReservationDao;
import nextstep.dto.ReservationDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = RoomEscapeWebApplication.class)
class ThemeReservationDaoTest {

    private final static Long EXIST_THEME_ID = 1L;
    private final static Long NOT_EXIST_THEME_ID = 1000L;
    public static final String RESERVATION_DATE = "2022-12-12";

    @Autowired
    private ThemeReservationDao themeReservationDao;

    @Test
    @DisplayName("방탈출 예약하기")
    void test1(){
        Reservation reservation = makeRandomReservation(RESERVATION_DATE, "14:01", EXIST_THEME_ID);

        Long reservationId = themeReservationDao.createReservation(reservation);
        Reservation findReservation = themeReservationDao.findById(reservationId);
        assertThat(findReservation).isNotNull();
    }

    @Test
    @DisplayName("이미 예약된 방탈출 예약을 취소한다.")
    void test2(){
        Reservation reservation = makeRandomReservation(RESERVATION_DATE, "14:02", EXIST_THEME_ID);
        Long reservationId = themeReservationDao.createReservation(reservation);

        themeReservationDao.deleteReservation(reservationId);
        assertThat(themeReservationDao.findById(reservationId)).isNull();
    }

    @Test
    @DisplayName("존재하지 않는 예약을 취소할 수 없다.")
    void test3(){
        assertThatThrownBy(() -> themeReservationDao.deleteReservation(1000L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약된 방을 조회한다.")
    void test4() {
        Reservation randomReservation = makeRandomReservation(RESERVATION_DATE, "14:02", EXIST_THEME_ID);
        long reservationId = themeReservationDao.createReservation(randomReservation);
        Reservation findReservation = themeReservationDao.findById(reservationId);

        assertThat(findReservation.getName()).isEqualTo(randomReservation.getName());
    }

    @Test
    @DisplayName("예약되지 않은 방을 조회한다.")
    void test5() {
        Reservation findReservation = themeReservationDao.findById(100L);

        assertThat(findReservation).isNull();
    }

    @Test
    @DisplayName("존재하지 않는 테마는 생성할 수 없다.")
    void test6(){
        Reservation reservation = makeRandomReservation(RESERVATION_DATE, "14:06", NOT_EXIST_THEME_ID);

        assertThatThrownBy(() -> themeReservationDao.createReservation(reservation))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("날짜와 시간이 같은 예약은 할 수 없다.")
    void test7() {
        Reservation reservation1 = makeRandomReservation(RESERVATION_DATE, "14:07", EXIST_THEME_ID);
        Reservation reservation2 = makeRandomReservation(RESERVATION_DATE, "14:07", EXIST_THEME_ID);

        themeReservationDao.createReservation(reservation1);
        Assertions.assertThatThrownBy(() -> themeReservationDao.createReservation(reservation2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    Reservation makeRandomReservation(String date, String time, Long themeId){
        List<String> names = List.of("omin", "ethan", "java");

        int index = ThreadLocalRandom.current().nextInt(3);

        return ReservationDto.from(new ReservationDto(date, time, names.get(index), themeId));
    }
}
