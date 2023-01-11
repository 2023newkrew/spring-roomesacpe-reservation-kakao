package nextstep.web;

import nextstep.dao.web.ReservationQueryingDAO;
import nextstep.domain.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ReservationQueryingDAOTest {
    private ReservationQueryingDAO reservationQueryingDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationQueryingDAO = new ReservationQueryingDAO(jdbcTemplate);

        List<Object[]> reservations = List.of(
                new Object[]{Date.valueOf("2022-08-11"), Time.valueOf("13:00:00"), "name", "워너고홈", "병맛 어드벤처 회사 코믹물", 29_000},
                new Object[]{Date.valueOf("2022-08-11"), Time.valueOf("14:00:00"), "name2", "워너고홈", "병맛 어드벤처 회사 코믹물", 29_000}
        );

        jdbcTemplate.batchUpdate("INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);", reservations);
    }

    @Test
    void findReservationById() {
        Reservation reservation = reservationQueryingDAO.findReservationById(1L);

        assertThat(reservation).isNotNull();
        assertThat(reservation.getName()).isEqualTo("name");
    }

    @Test
    void findReservationByDateAndTime() {
        List<Reservation> reservations = reservationQueryingDAO.findReservationByDateAndTime(
                LocalDate.of(2022, 8, 11),
                LocalTime.of(13, 0)
        );

        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).getName()).isEqualTo("name");
    }
}