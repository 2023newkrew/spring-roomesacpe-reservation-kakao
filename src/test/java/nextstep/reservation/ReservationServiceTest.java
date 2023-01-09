package nextstep.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationServiceTest {

    private Theme theme;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        this.theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        this.reservationService = new ReservationService(new ReservationRepository());
    }

    @Test
    @DisplayName("예약 생성")
    void createReservationTest() {
        Reservation reservation = new Reservation(1L, LocalDate.parse("2022-08-11"), LocalTime.parse("13:00"), "name", theme);
        Reservation result = reservationService.create(LocalDate.parse("2022-08-11"), LocalTime.parse("13:00"), "name", theme);
        assertEquals(result, reservation);
    }
}
