package nextstep.reservation;

import nextstep.reservation.exception.CreateReservationException;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ReservationRepositoryTest {
    private ReservationRepository reservationRepository;
    private Theme theme;

    @BeforeEach
    void setUp() {
        reservationRepository = new MemoryReservationRepository();
        theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        reservationRepository.create(new Reservation(null, LocalDate.parse("2022-08-11"), LocalTime.parse("13:00"), "name", theme));
    }

    @AfterEach
    void tearDown() {
        reservationRepository.clear();
    }

    @Test
    @DisplayName("예약 삽입")
    void createReservationTest() {
        Reservation reservation = new Reservation(null, LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name", theme);
        Reservation result = reservationRepository.create(reservation);
        assertEquals(result, reservation);
    }

    @Test
    @DisplayName("예약 ID로 조회")
    void findByIdTest() {
        Reservation reservation = new Reservation(1L, LocalDate.parse("2022-08-11"), LocalTime.parse("13:00"), "name", theme);

        Reservation result = reservationRepository.findById(1L);
        assertEquals(result, reservation);
    }

    @Test
    @DisplayName("예약 날짜/시간 조회")
    void findByDateTimeTest() {
        Boolean result = reservationRepository.findByDateTime(LocalDate.parse("2022-08-11"), LocalTime.parse("13:00"));
        assertEquals(result, true);
    }

    @Test
    @DisplayName("없는 예약 날짜/시간 조회")
    void findByDateTimeEmptyTest() {
        Boolean result = reservationRepository.findByDateTime(LocalDate.parse("2022-08-14"), LocalTime.parse("13:00"));
        assertEquals(result, false);
    }

    @Test
    @DisplayName("중복 시간에 예약시 예외 발생")
    void duplicateTimeReservationThrowException() {
        Assertions.assertThrows(CreateReservationException.class,
                () -> reservationRepository.create(
                        new Reservation(null, LocalDate.parse("2022-08-11"), LocalTime.parse("13:00"), "name", theme)
                ));
    }

    @Test
    @DisplayName("예약 삭제")
    void deleteReservation() {
        long id = 1L;
        Boolean result = reservationRepository.delete(id);
        assertEquals(result,true);
        assertNull(reservationRepository.findById(id));
    }
}