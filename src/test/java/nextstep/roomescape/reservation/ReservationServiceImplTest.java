package nextstep.roomescape.reservation;

import nextstep.roomescape.reservation.domain.Reservation;
import nextstep.roomescape.reservation.domain.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationServiceImplTest {

    private Theme theme;
    private ReservationServiceImpl reservationServiceImpl;

    @Transactional
    @BeforeEach
    void setUp() {
        this.theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        this.reservationServiceImpl = new ReservationServiceImpl(new ReservationRepositoryMemoryImpl());
    }

    @DisplayName("예약 생성")
    @Transactional
    @Test
    void createReservationTest() {
        Reservation reservation = createRequest(LocalDate.parse("9999-01-11"));
        Reservation result = reservationServiceImpl.create(reservation);
        assertEquals(result, reservation);
    }

    private Reservation createRequest(LocalDate date) {
        final LocalTime time = LocalTime.parse("13:00");
        final String name = "kakao";
        return new Reservation(null, date, time, name, theme);
    }
}
