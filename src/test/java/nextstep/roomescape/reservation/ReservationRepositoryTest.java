package nextstep.roomescape.reservation;

import nextstep.roomescape.reservation.model.Reservation;
import nextstep.roomescape.reservation.model.Theme;
import nextstep.roomescape.reservation.exception.CreateReservationException;
import nextstep.roomescape.reservation.repository.ReservationRepository;
import nextstep.roomescape.reservation.repository.ReservationRepositoryMemoryImpl;
import org.junit.jupiter.api.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ReservationRepositoryTest {
    private ReservationRepository reservationRepository;
    private Theme theme;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepositoryMemoryImpl();
        theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
    }


    @DisplayName("예약 삽입")
    @Transactional
    @Test
    void createReservationTest() {
        Reservation reservation = createRequest(LocalDate.parse("9999-01-02"));
        Reservation result = reservationRepository.create(reservation);
        assertEquals(result, reservation);
    }

    @DisplayName("예약 ID로 조회")
    @Transactional
    @Test
    void findByIdTest() {
        Reservation reservation = reservationRepository.create(createRequest(LocalDate.parse("9999-02-02")));
        Reservation result = reservationRepository.findById(reservation.getId());
        assertEquals(result, reservation);
    }

    @DisplayName("예약 날짜/시간 조회")
    @Transactional
    @Test
    void findByDateTimeTest() {
        reservationRepository.create(createRequest(LocalDate.parse("9999-03-03")));
        Boolean result = reservationRepository.findByDateTime(LocalDate.parse("9999-03-03"), LocalTime.parse("13:00"));
        assertEquals(result, true);
    }

    @DisplayName("없는 예약 날짜/시간 조회")
    @Test
    void findByDateTimeEmptyTest() {
        Boolean result = reservationRepository.findByDateTime(LocalDate.parse("9966-08-14"), LocalTime.parse("13:00"));
        assertEquals(result, false);
    }

    @DisplayName("중복 시간에 예약시 예외 발생")
    @Transactional
    @Test
    void duplicateTimeReservationThrowException() {
        reservationRepository.create(createRequest(LocalDate.parse("9999-04-04")));
        Assertions.assertThrows(CreateReservationException.class,
                () -> reservationRepository.create(
                        new Reservation(null, LocalDate.parse("9999-04-04"), LocalTime.parse("13:00"), "name", theme)
                ));
    }

    @DisplayName("예약 삭제")
    @Transactional
    @Test
    void deleteReservation() {
        Reservation reservation = reservationRepository.create(createRequest(LocalDate.parse("9999-05-05")));
        long id = reservation.getId();
        Boolean result = reservationRepository.delete(id);
        assertEquals(result,true);
        assertNull(reservationRepository.findById(id));
    }

    private Reservation createRequest(LocalDate date) {
        final LocalTime time = LocalTime.parse("13:00");
        final String name = "kakao";
        return new Reservation(null, date, time, name, theme);
    }
}