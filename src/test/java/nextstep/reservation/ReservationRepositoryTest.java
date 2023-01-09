package nextstep.reservation;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationRepositoryTest {
    private static ReservationRepository reservationRepository;
    private static Theme theme;
    @BeforeAll
    static void beforeAll() {
        reservationRepository = new ReservationRepository();
        theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        reservationRepository.create(LocalDate.parse("2022-08-11"), LocalTime.parse("13:00"), "name", theme);
    }

    @Test
    @DisplayName("예약 삽입")
    void createReservationTest() {
        reservationRepository.create(LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name", theme);
        assertEquals(reservationRepository.getReservationList().size(), 2);
    }

    @Test
    @DisplayName("예약 ID로 조회")
    void findByIdTest() {
        Reservation reservation = new Reservation(1L, LocalDate.parse("2022-08-11"), LocalTime.parse("13:00"), "name", theme);

        Reservation result = reservationRepository.findById(1L);
        assertEquals(result, reservation);
    }

    @Test
    void findByDateTimeTest(){
        Boolean result = reservationRepository.findByDateTime(LocalDate.parse("2022-08-11"), LocalTime.parse("13:00"));
        assertEquals(result, true);
    }

    @Test
    void findByDateTimeEmptyTest(){
        Boolean result = reservationRepository.findByDateTime(LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"));
        assertEquals(result, false);
    }

    @Test
    void duplicateTimeReservationThrowException() {
        Assertions.assertThrows(RuntimeException.class, () ->  reservationRepository.create(LocalDate.parse("2022-08-11"), LocalTime.parse("13:00"), "name", theme));
    }
}