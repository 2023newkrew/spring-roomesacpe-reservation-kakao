package roomescape.service.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.dao.reservation.ReservationDAO;
import roomescape.dto.Reservation;

@DisplayName("예약 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    private static final Long ID_DATA = 1L;
    private static final LocalDate DATE_DATA = LocalDate.parse("2022-08-01");
    private static final LocalTime TIME_DATA = LocalTime.parse("13:00");
    private static final String NAME_DATA = "test";
    private static final Long THEME_ID_DATA = 1L;

    private ReservationServiceInterface reservationService;

    @Mock
    private ReservationDAO reservationDAO;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(reservationDAO);
    }

    @DisplayName("예약 생성")
    @Test
    void createReservation() {
        Reservation reservation = new Reservation(DATE_DATA, TIME_DATA, NAME_DATA, THEME_ID_DATA);

        when(reservationDAO.exist(reservation)).thenReturn(false);
        when(reservationDAO.create(reservation)).thenReturn(ID_DATA);

        assertThat(reservationService.create(reservation)).isEqualTo(ID_DATA);
    }

    @DisplayName("예약 조회")
    @Test
    void listReservation() {
        Reservation reservation = new Reservation(DATE_DATA, TIME_DATA, NAME_DATA, THEME_ID_DATA);

        when(reservationDAO.find(ID_DATA)).thenReturn(reservation);

        Reservation actual = reservationService.find(ID_DATA);
        assertThat(actual.getDate()).isEqualTo(DATE_DATA);
        assertThat(actual.getTime()).isEqualTo(TIME_DATA);
        assertThat(actual.getName()).isEqualTo(NAME_DATA);
        assertThat(actual.getThemeId()).isEqualTo(THEME_ID_DATA);
    }
}
