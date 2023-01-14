package roomescape.dao.reservation.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.reservation.ConsoleReservationDAO;
import roomescape.dao.reservation.ReservationDAO;
import roomescape.dto.Reservation;

@DisplayName("콘솔용 데이터베이스 접근 예외처리")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
@Sql("classpath:/test.sql")
public class ConsoleReservationDAOExceptionTest {

    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private final ReservationDAO reservationDAO = new ConsoleReservationDAO(URL, USER, PASSWORD);

    @DisplayName("예약 조회) ID가 존재하지 않는 경우 null 리턴")
    @Test
    void findReservation() {
        Reservation reservation = reservationDAO.find(10L);
        assertThat(reservation).isNull();
    }
}
