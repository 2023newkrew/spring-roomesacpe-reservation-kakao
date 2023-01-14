package roomescape.dao.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import roomescape.dto.Reservation;

@DisplayName("콘솔용 데이터베이스 접근 테스트")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql("classpath:/test.sql")
public class ConsoleReservationDAOTest {

    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static final LocalDate DATE_DATA1 = LocalDate.parse("2022-08-01");
    private static final LocalDate DATE_DATA2 = LocalDate.parse("2022-08-02");
    private static final LocalTime TIME_DATA = LocalTime.parse("13:00");
    private static final String NAME_DATA = "test";
    private static final Long THEME_ID_DATA = 2L;

    private static final String COUNT_SQL = "SELECT count(*) FROM RESERVATION";

    private final ReservationDAO reservationDAO = new ConsoleReservationDAO(URL, USER, PASSWORD);
    private Connection con;

    @BeforeEach
    void setUp() throws SQLException {
        con = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @AfterEach
    void setDown() throws SQLException {
        if (con != null) {
            con.close();
        }
    }

    private Long getCount() throws SQLException {
        ResultSet resultSet = con.createStatement().executeQuery(COUNT_SQL);
        assertThat(resultSet.next()).isTrue();
        return resultSet.getLong(1);
    }

    @DisplayName("예약 생성")
    @Test
    void insertReservation() throws SQLException {
        long count = getCount();

        Reservation reservation = new Reservation(DATE_DATA2, TIME_DATA, NAME_DATA, THEME_ID_DATA);

        reservationDAO.create(reservation);

        long actual = getCount();
        assertThat(actual).isEqualTo(count + 1L);
    }

    @DisplayName("예약 조회")
    @Test
    void findReservation() {
        Reservation reservation = reservationDAO.find(1L);

        assertThat(reservation.getName()).isEqualTo(NAME_DATA);
        assertThat(reservation.getDate()).isEqualTo(DATE_DATA1);
        assertThat(reservation.getTime()).isEqualTo(TIME_DATA);
        assertThat(reservation.getThemeId()).isEqualTo(THEME_ID_DATA);
    }

    @DisplayName("예약 삭제")
    @Test
    void removeReservation() throws SQLException {
        long count = getCount();

        reservationDAO.remove(1L);

        long actual = getCount();
        assertThat(actual).isEqualTo(count - 1L);
    }

    @DisplayName("예약 존재 확인")
    @Test
    void existReservation() {
        Reservation reservation1 = new Reservation(DATE_DATA1, TIME_DATA, NAME_DATA, THEME_ID_DATA);
        Reservation reservation2 = new Reservation(DATE_DATA2, TIME_DATA, NAME_DATA, THEME_ID_DATA);

        assertThat(reservationDAO.exist(reservation1)).isTrue();
        assertThat(reservationDAO.exist(reservation2)).isFalse();
    }

    @DisplayName("예약 아이디 존재 확인")
    @Test
    void existReservationId() {
        assertThat(reservationDAO.existId(1L)).isTrue();
        assertThat(reservationDAO.existId(2L)).isFalse();
    }
}
