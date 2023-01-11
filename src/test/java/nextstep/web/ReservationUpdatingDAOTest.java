package nextstep.web;

import nextstep.dao.web.ReservationQueryingDAO;
import nextstep.dao.web.ReservationUpdatingDAO;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ReservationUpdatingDAOTest {
    @Autowired
    private ReservationUpdatingDAO reservationUpdatingDAO;
    @Autowired
    private ReservationQueryingDAO reservationQueryingDAO;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        List<Object[]> reservations = List.of(
                new Object[]{Date.valueOf("2022-08-11"), Time.valueOf("13:00:00"), "name", "워너고홈", "병맛 어드벤처 회사 코믹물",
                        29_000},
                new Object[]{Date.valueOf("2022-08-11"), Time.valueOf("14:00:00"), "name2", "워너고홈", "병맛 어드벤처 회사 코믹물",
                        29_000}
        );

        jdbcTemplate.batchUpdate(
                "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);",
                reservations);
    }

    @Test
    void insertWithKeyHolder() {
        Reservation reservation = new Reservation(
                LocalDate.of(2022, 8, 11),
                LocalTime.of(13, 0),
                "name3",
                new Theme(
                        "워너고홈",
                        "병맛 어드벤처 회사 코믹물",
                        29000
                )
        );
        Long id = reservationUpdatingDAO.insertWithKeyHolder(reservation);

        assertThat(id).isNotNull();

        Reservation reservationById = reservationQueryingDAO.findReservationById(id);
        assertThat(reservationById).isNotNull();
        assertThat(reservationById.getName()).isEqualTo("name3");
    }

    @Test
    void delete() {
        int rowNum = reservationUpdatingDAO.delete(1L);

        assertThat(rowNum).isEqualTo(1);
    }
}
