package roomescape.dao.reservation.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.connection.ConnectionManager;
import roomescape.dao.reservation.ConsoleReservationDAO;
import roomescape.dao.reservation.ReservationDAO;
import roomescape.dto.Reservation;

@DisplayName("콘솔용 데이터베이스 접근 - 예약 테이블이 존재하지 않을 경우 null 리턴")
@JdbcTest
@Rollback(false)
@ActiveProfiles("test")
@Sql(value = {"classpath:/drop.sql"})
@Sql(value = {"classpath:/drop.sql", "classpath:/schema.sql", "classpath:/data.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class ConsoleReservationDAONoTableExceptionTest {

    private static final LocalDate DATE_DATA = LocalDate.parse("2022-08-01");
    private static final LocalTime TIME_DATA = LocalTime.parse("13:00");
    private static final String NAME_DATA = "test";
    private static final Long THEME_ID_DATA = 2L;

    @Autowired
    private DataSource dataSource;

    private ReservationDAO reservationDAO;

    @BeforeEach
    void setUp() {
        ConnectionManager connectionManager = new ConnectionManager(dataSource);
        reservationDAO = new ConsoleReservationDAO(connectionManager);
    }

    @DisplayName("예약 생성) 테이블이 존재하지 않을 경우 null 리턴")
    @Test
    void returnNullWhenCreateIfNotExistingTable() {
        Reservation reservation = new Reservation(DATE_DATA, TIME_DATA, NAME_DATA, THEME_ID_DATA);
        assertThat(reservationDAO.create(reservation)).isNull();
    }

    @DisplayName("예약 조회) 테이블이 존재하지 않을 경우 null 리턴")
    @Test
    void returnNullWhenFindIfNotExistingTable() {
        assertThat(reservationDAO.find(1L)).isNull();
    }

    @DisplayName("예약 존재 확인) 테이블이 존재하지 않을 경우 null 리턴")
    @Test
    void returnNullWhenExistIfNotExistingTable() {
        Reservation reservation = new Reservation(DATE_DATA, TIME_DATA, NAME_DATA, THEME_ID_DATA);
        assertThat(reservationDAO.exist(reservation)).isNull();
    }

    @DisplayName("예약 아이디 존재 확인) 테이블이 존재하지 않을 경우 null 리턴")
    @Test
    void returnNullWhenExistIdIfNotExistingTable() {
        assertThat(reservationDAO.existId(1L)).isNull();
    }

    @DisplayName("예약에서 테마 아이디 존재 확인) 테이블이 존재하지 않을 경우 null 리턴")
    @Test
    void returnNullWhenExistThemeIdIfNotExistingTable() {
        assertThat(reservationDAO.existThemeId(1L)).isNull();
    }
}
