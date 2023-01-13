package nextstep.reservation;

import nextstep.reservation.entity.Reservation;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.exception.ReservationException;
import nextstep.reservation.repository.ReservationRepository;
import nextstep.reservation.repository.ThemeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static nextstep.reservation.exception.ReservationExceptionCode.NO_SUCH_RESERVATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@Transactional
class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ThemeRepository themeRepository;
    private Reservation reservation;
    private Reservation reservationDuplicated;

    @BeforeEach
    void setUp() {
        Theme theme = new Theme(null, "워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        themeRepository.save(theme);

        this.reservation = new Reservation(null, LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name", 1L);
        this.reservationDuplicated = new Reservation(null, LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name2", 1L);
    }

    @Test
    @DisplayName("예약 삽입")
    void createReservationTest() {
        Reservation created = reservationRepository.save(reservation);
        assertThat(reservationDataEquals(created, reservation)).isTrue();
    }

    @Test
    @DisplayName("예약 ID로 조회")
    void findByIdTest() {
        Reservation created = reservationRepository.save(reservation);
        Reservation result = reservationRepository.findById(created.getId());
        assertThat(result).isEqualTo(created);
    }

    @Test
    @DisplayName("날짜/시간으로 예약 존재 여부 조회(예약 있을 때)")
    void findByDateTimeTest() {
        reservationRepository.save(reservation);
        Boolean result = reservationRepository.findByDateAndTime(reservation.getDate(), reservation.getTime());
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("날짜/시간으로 예약 존재 여부 조회(예약 없을 때")
    void findByDateTimeEmptyTest() {
        Boolean result = reservationRepository.findByDateAndTime(LocalDate.parse("2022-08-14"), LocalTime.parse("13:00"));
        assertThat(result).isEqualTo(false);
    }

    @Test
    @DisplayName("이미 예약이 존재하는 시간에 예약 생성 시도할 때 예외 발생")
    void duplicateTimeReservationThrowException() {
        reservationRepository.save(reservation);
        Assertions.assertThatThrownBy(
                () -> reservationRepository.save(reservationDuplicated)
        ).isInstanceOf(ReservationException.class);
    }

    @Test
    @DisplayName("예약 삭제")
    void deleteReservation() {
        Reservation created = reservationRepository.save(reservation);
        Boolean result = reservationRepository.deleteById(created.getId());
        assertThat(result).isEqualTo(true);

        assertThatThrownBy(
                () -> reservationRepository.findById(created.getId()))
                .isInstanceOf(ReservationException.class)
                .hasMessage(NO_SUCH_RESERVATION.getMessage());
    }

    private boolean reservationDataEquals(Reservation a, Reservation b) {
        return a.getName().equals(b.getName()) &&
                a.getDate().equals(b.getDate()) &&
                a.getTime().equals(b.getTime()) &&
                a.getThemeId().equals(b.getThemeId());
    }
}