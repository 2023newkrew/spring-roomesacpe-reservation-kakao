package roomescape.dao;

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
import roomescape.dto.Reservation;
import roomescape.dto.Theme;

@DisplayName("콘솔용 데이터베이스 접근 테스트")
public class ConsoleReservationDAOTest {

    private static final String URL = "jdbc:h2:~/test;AUTO_SERVER=true";
    private static final String USER = "";
    private static final String PASSWORD = "";

    private static final LocalDate DATE_DATA1 = LocalDate.parse("2022-08-01");
    private static final LocalDate DATE_DATA2 = LocalDate.parse("2022-08-02");
    private static final LocalTime TIME_DATA = LocalTime.parse("13:00");
    private static final String NAME_DATA = "test";
    private static final String THEME_NAME_DATA = "워너고홈";
    private static final String THEME_DESC_DATA = "병맛 어드벤처 회사 코믹물";
    private static final Integer THEME_PRICE_DATA = 29000;

    private static final Theme THEME_DATA = new Theme(
            THEME_NAME_DATA, THEME_DESC_DATA, THEME_PRICE_DATA);

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS reservation";
    private static final String CREATE_TABLE =
            "CREATE TABLE reservation "
                    + "(id bigint not null auto_increment,"
                    + " date date,"
                    + " time time,"
                    + " name varchar(20),"
                    + " theme_name varchar(20),"
                    + " theme_desc varchar(20),"
                    + " theme_price int,"
                    + " primary key (id));";
    private static final String COUNT_SQL = "SELECT count(*) FROM RESERVATION";
    private static final String ADD_DATE1_SQL =
            "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) "
                    + "VALUES ('" + DATE_DATA1 + "', '" + TIME_DATA + "', '" + NAME_DATA + "', '"
                    + THEME_NAME_DATA + "', '" + THEME_DESC_DATA + "', '" + THEME_PRICE_DATA + "');";

    private final ReservationDAO reservationDAO = new ConsoleReservationDAO(URL, USER, PASSWORD);
    private Connection con;

    @BeforeEach
    void setUp() throws SQLException {
        con = DriverManager.getConnection(URL, USER, PASSWORD);
        con.createStatement().executeUpdate(DROP_TABLE);
        con.createStatement().executeUpdate(CREATE_TABLE);

        con.createStatement().executeUpdate(ADD_DATE1_SQL);
    }

    @AfterEach
    void setDown() throws SQLException {
        if (con != null) {
            con.close();
        }
    }

    @DisplayName("예약 생성")
    @Test
    void addReservation() throws SQLException {
        Reservation reservation = new Reservation(DATE_DATA2, TIME_DATA, NAME_DATA, THEME_DATA);

        reservationDAO.addReservation(reservation);
        ResultSet resultSet = con.createStatement().executeQuery(COUNT_SQL);
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.getInt(1)).isEqualTo(2);
    }

    @DisplayName("예약 조회")
    @Test
    void findReservation() {
        Reservation reservation = reservationDAO.findReservation(1L);
        assertThat(reservation.getName()).isEqualTo(NAME_DATA);
        assertThat(reservation.getDate()).isEqualTo(DATE_DATA1);
        assertThat(reservation.getTime()).isEqualTo(TIME_DATA);
        assertThat(reservation.getTheme().getName()).isEqualTo(THEME_NAME_DATA);
        assertThat(reservation.getTheme().getDesc()).isEqualTo(THEME_DESC_DATA);
        assertThat(reservation.getTheme().getPrice()).isEqualTo(THEME_PRICE_DATA);
    }

    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() throws SQLException {
        reservationDAO.deleteReservation(1L);
        ResultSet resultSet = con.createStatement().executeQuery(COUNT_SQL);
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.getInt(1)).isEqualTo(0);
    }
}
