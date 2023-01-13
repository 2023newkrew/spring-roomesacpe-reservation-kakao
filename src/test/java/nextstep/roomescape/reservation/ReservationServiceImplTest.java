package nextstep.roomescape.reservation;

import nextstep.roomescape.reservation.controller.dto.ReservationResponseDTO;
import nextstep.roomescape.reservation.controller.dto.ReservationRequestDTO;
import nextstep.roomescape.reservation.model.Theme;
import nextstep.roomescape.reservation.repository.ReservationRepositoryMemoryImpl;
import nextstep.roomescape.reservation.service.ReservationServiceImpl;
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
        ReservationRequestDTO reservation = createRequest(LocalDate.parse("9999-01-11"));
        ReservationResponseDTO result = reservationServiceImpl.create(reservation);
        assertEquals(result.getDate(), reservation.getDate());
        assertEquals(result.getTime(), reservation.getTime());
        assertEquals(result.getName(), reservation.getName());
    }

    private ReservationRequestDTO createRequest(LocalDate date) {
        final LocalTime time = LocalTime.parse("13:00");
        final String name = "kakao";
        return new ReservationRequestDTO(date, time, name, theme);
    }
}
