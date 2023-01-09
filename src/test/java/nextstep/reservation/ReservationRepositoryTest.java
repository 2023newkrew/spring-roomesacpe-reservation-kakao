package nextstep.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationRepositoryTest {
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        this.reservationRepository = new ReservationRepository();
    }

    @Test
    @DisplayName("예약 삽입")
    void createReservationTest() {
        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        reservationRepository.create("2022-08-11", "13:00", "name", theme);

        assertEquals(reservationRepository.getReservationList().size(), 1);

    }

    @Test
    void findByIdTest() {
        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        Reservation reservation = new Reservation(1L, LocalDate.parse("2022-08-11"), LocalTime.parse("13:00"), "name", theme);
        reservationRepository.create("2022-08-11", "13:00", "name", theme);

        Reservation result = reservationRepository.findById(1L);
        assertEquals(result, reservation);

    }
}