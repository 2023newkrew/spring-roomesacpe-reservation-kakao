package roomescape.dao.reservation.exception;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ActiveProfiles;
import roomescape.connection.ConnectionManager;
import roomescape.dao.reservation.ConsoleReservationDAO;
import roomescape.dao.reservation.ReservationDAO;
import roomescape.dto.Reservation;

@DisplayName("콘솔용 데이터베이스 접근 예외처리")
@JdbcTest
@ActiveProfiles("test")
public class ConsoleReservationDAOExceptionTest {

    @Autowired
    private DataSource dataSource;

    private ReservationDAO reservationDAO;

    @BeforeEach
    void setUp() {
        ConnectionManager connectionManager = new ConnectionManager(dataSource);
        reservationDAO = new ConsoleReservationDAO(connectionManager);
    }

    @DisplayName("예약 조회) ID가 존재하지 않는 경우 null 리턴")
    @Test
    void returnNullWhenFindNotExistingId() {
        Reservation reservation = reservationDAO.find(10L);
        assertThat(reservation).isNull();
    }
}
