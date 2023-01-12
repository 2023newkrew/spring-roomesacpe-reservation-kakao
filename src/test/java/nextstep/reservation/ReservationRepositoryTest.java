package nextstep.reservation;

import nextstep.reservation.entity.Reservation;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.exception.ReservationException;
import nextstep.reservation.repository.ReservationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;
    private Reservation reservation;
    private Reservation reservationDuplicated;

    @BeforeEach
    void setUp() {
        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        this.reservation = new Reservation(null, LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name", theme);
        this.reservationDuplicated = new Reservation(null, LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name2", theme);
    }

    @AfterEach
    void tearDown() {
        reservationRepository.clear();
    }

    @Test
    @DisplayName("예약 삽입")
    void createReservationTest() {
        Reservation result = reservationRepository.create(reservation);
        assertThat(result).isEqualTo(reservation);
    }

    @Test
    @DisplayName("예약 ID로 조회")
    void findByIdTest() {
        Reservation created = reservationRepository.create(reservation);
        Reservation result = reservationRepository.findById(created.getId());
        assertThat(result).isEqualTo(created);
    }

    @Test
    @DisplayName("날짜/시간으로 예약 존재 여부 조회(예약 있을 때)")
    void findByDateTimeTest() {
        reservationRepository.create(reservation);
        Boolean result = reservationRepository.findByDateTime(reservation.getDate(), reservation.getTime());
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("날짜/시간으로 예약 존재 여부 조회(예약 없을 때")
    void findByDateTimeEmptyTest() {
        Boolean result = reservationRepository.findByDateTime(LocalDate.parse("2022-08-14"), LocalTime.parse("13:00"));
        assertThat(result).isEqualTo(false);
    }

    @Test
    @DisplayName("이미 예약이 존재하는 시간에 예약 생성 시도할 때 예외 발생")
    void duplicateTimeReservationThrowException() {
        reservationRepository.create(reservation);
        Assertions.assertThatThrownBy(
                () -> reservationRepository.create(reservationDuplicated)
        ).isInstanceOf(ReservationException.class);
    }

    @Test
    @DisplayName("예약 삭제")
    void deleteReservation() {
        Reservation created = reservationRepository.create(reservation);
        Boolean result = reservationRepository.delete(created.getId());
        assertThat(result).isEqualTo(true);

        Reservation findResult = reservationRepository.findById(created.getId());
        assertThat(findResult).isNull();
    }
}