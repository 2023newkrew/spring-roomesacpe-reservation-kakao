package nextstep;

import domain.Reservation;
import kakao.repository.ThemeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDAOTest {
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";
    private final ReservationDAO reservationDAO = new ReservationDAO();

    private final Reservation reservation = Reservation.builder()
            .name("baker")
            .date(LocalDate.of(2022, 10, 13))
            .time(LocalTime.of(13, 00))
            .theme(ThemeRepository.theme)
            .build();

    @BeforeEach
    void setUp() {
        try (Connection con = DriverManager.getConnection(DB_URL, "sa", "");
             PreparedStatement truncatePs = con.prepareStatement("truncate table reservation");
             PreparedStatement restartPs = con.prepareStatement("ALTER TABLE reservation ALTER COLUMN ID RESTART WITH 1")) {

            truncatePs.executeUpdate();
            restartPs.execute();
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
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
        Assertions.assertThat(cp.getTheme()).isEqualTo(reservation.getTheme());
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

