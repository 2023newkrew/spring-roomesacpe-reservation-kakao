package nextstep;

import domain.Reservation;
import domain.Theme;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

class ReservationDAOTest {
    private final ReservationDAO reservationDAO = new ReservationDAO();

    private final Reservation reservation = Reservation.builder()
            .date(LocalDate.of(2022, 10, 13))
            .time(LocalTime.of(13, 00))
            .name("baker")
            .theme(new Theme(
                    1L,
                    "themeName",
                    "themeDesc",
                    1000
            ))
            .build();

    private final Initiator initiator = new Initiator();

    @BeforeEach
    void setUp() {
        initiator.initReservation();
        initiator.initTheme();
        initiator.createSingleTheme();
    }

    @DisplayName("resrvation을 저장하고 저장된 id를 반환한다")
    @Test
    void createReservation() {
        Assertions.assertThat(reservationDAO.addReservation(reservation)).isOne();
    }

    @DisplayName("id로 저장된 reservation을 조회한다")
    @Test
    void findById() {
        reservationDAO.addReservation(reservation);
        Reservation cp = reservationDAO.findById(1L);

        Assertions.assertThat(cp.getName()).isEqualTo(reservation.getName());
        Assertions.assertThat(cp.getDate()).isEqualTo(reservation.getDate());
        Assertions.assertThat(cp.getTime()).isEqualTo(reservation.getTime());
        Assertions.assertThat(cp.getThemeId()).isEqualTo(reservation.getThemeId());
    }

    @DisplayName("id에 대응되는 resrevation이 없으면 null을 반환한다")
    @Test
    void findByIllegalId() {
        Assertions.assertThat(reservationDAO.findById(10L)).isNull();
    }

    @DisplayName("id를 받아 해당하는 reservation을 삭제한다, 삭제되면 1을 반환한다")
    @Test
    void delete() {
        reservationDAO.addReservation(reservation);

        Assertions.assertThat(reservationDAO.delete(1L)).isOne();
    }

    @DisplayName("id에 대응되는 reservation이 없으면 0을 반환한다")
    @Test
    void deleteInvalidId() {
        Assertions.assertThat(reservationDAO.delete(10L)).isZero();
    }
}

