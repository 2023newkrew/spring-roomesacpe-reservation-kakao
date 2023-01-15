package roomescape.dao.reservation.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import roomescape.dao.reservation.ReservationDAO;
import roomescape.dao.reservation.SpringReservationDAO;
import roomescape.dto.Reservation;

@DisplayName("JDBC 데이터베이스 접근 예외처리")
@JdbcTest
@ActiveProfiles("test")
@Sql("classpath:/test.sql")
public class SpringReservationDAOExceptionTest {
    private ReservationDAO reservationDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationDAO = new SpringReservationDAO(jdbcTemplate);
    }

    @DisplayName("예약 조회) ID가 존재하지 않는 경우 null 리턴")
    @Test
    void returnNullWhenFindNotExistingId() {
        Reservation reservation = reservationDAO.find(10L);
        assertThat(reservation).isNull();
    }
}
