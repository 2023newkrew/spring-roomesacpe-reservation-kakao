package nextstep.repository;

import nextstep.domain.theme.Theme;
import nextstep.domain.reservation.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    private Reservation testReservation = new Reservation(
            LocalDate.parse("2000-01-01"),
            LocalTime.parse("00:00"),
            "name",
            new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000)
    );

    @BeforeEach
    void setUp() {
        this.webAppReservationRepo.reset();
    }

    @DisplayName("can store reservation")
    @Test
    void can_store_reservation() {
        long id = this.webAppReservationRepo.add(this.testReservation);
        assertNotNull(id);
    }

    @DisplayName("can find by id")
    @Test
    void can_find_by_id() {
        long id = this.webAppReservationRepo.add(this.testReservation);
        Reservation reservation = this.webAppReservationRepo.findById(id);
        assertTrue(reservation.equals(this.testReservation));
    }

    @DisplayName("can return null for nonexistent id")
    @Test
    void can_return_null_for_nonexistent_id() {
        long nonexistentId = this.webAppReservationRepo.add(this.testReservation) + 1;
        Reservation nonexistent = this.webAppReservationRepo.findById(nonexistentId);
        assertNull(nonexistent);
    }

    @DisplayName("can count when date and time match")
    @Test
    void can_count_when_date_and_time_match() {
        int CYCLE = 17;
        for (int i = 0; i < CYCLE; i++) {
            this.webAppReservationRepo.add(this.testReservation);
        }
        int count = this.webAppReservationRepo.countWhenDateAndTimeMatch(
                Date.valueOf(this.testReservation.getDate()),
                Time.valueOf(this.testReservation.getTime()));
        assertEquals(CYCLE, count);
    }

    @DisplayName("can delete by id")
    @Test
    void can_delete_by_id() {
        long id = this.webAppReservationRepo.add(this.testReservation);
        int code = this.webAppReservationRepo.delete(id);
        assertEquals(1, code);

        Reservation reservation = webAppReservationRepo.findById(id);
        assertNull(reservation);
    }
}
