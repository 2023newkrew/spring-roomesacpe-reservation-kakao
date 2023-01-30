package nextstep.repository;

import nextstep.domain.reservation.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class WebAppReservationRepoTest {
    @Autowired
    private WebAppReservationRepo webAppReservationRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Reservation testReservation = new Reservation(
            LocalDate.parse("2000-01-01"),
            LocalTime.parse("00:00"),
            "name",
            1L);

    @BeforeEach
    void setUp() {
        String sql = "TRUNCATE TABLE reservation";
        jdbcTemplate.update(sql);
    }

    @DisplayName("can store reservation")
    @Test
    void can_store_reservation() {
        long id = this.webAppReservationRepo.save(this.testReservation);
        assertNotNull(id);
    }

    @DisplayName("can find by id")
    @Test
    void can_find_by_id() {
        long id = this.webAppReservationRepo.save(this.testReservation);
        Reservation reservation = this.webAppReservationRepo.findById(id);
        assertTrue(reservation.equals(this.testReservation));
    }

    @DisplayName("can return null for nonexistent id")
    @Test
    void can_return_null_for_nonexistent_id() {
        long nonexistentId = this.webAppReservationRepo.save(this.testReservation) + 1;
        Reservation nonexistent = this.webAppReservationRepo.findById(nonexistentId);
        assertNull(nonexistent);
    }

    @DisplayName("can find by date and time and theme id")
    @Test
    void can_find_by_date_and_time_and_theme_id() {
        int CYCLE = 17;
        for (int i = 0; i < CYCLE; i++) {
            this.webAppReservationRepo.save(this.testReservation);
        }
        int count = this.webAppReservationRepo.findByDateAndTimeAndTheme(
                Date.valueOf(this.testReservation.getDate()),
                Time.valueOf(this.testReservation.getTime()),
                this.testReservation.getThemeId());
        assertEquals(CYCLE, count);
    }

    @DisplayName("can delete by id")
    @Test
    void can_delete_by_id() {
        long id = this.webAppReservationRepo.save(this.testReservation);
        int code = this.webAppReservationRepo.delete(id);
        assertEquals(1, code);

        Reservation reservation = webAppReservationRepo.findById(id);
        assertNull(reservation);
    }
}
