package nextstep.repository;

import nextstep.domain.theme.Theme;
import nextstep.domain.reservation.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleReservationRepoTest {
    private ConsoleReservationRepo consoleReservationRepo = new ConsoleReservationRepo();
    private Reservation testReservation = new Reservation(
            LocalDate.parse("2000-01-01"),
            LocalTime.parse("00:00"),
            "name",
            new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000)
    );

    @BeforeEach
    void setUp() {
        this.consoleReservationRepo.reset();
    }

    @DisplayName("can store reservation")
    @Test
    void can_store_reservation() {
        long id = this.consoleReservationRepo.add(this.testReservation);
        assertNotNull(id);
    }

    @DisplayName("can find by id")
    @Test
    void can_find_by_id() {
        long id = this.consoleReservationRepo.add(this.testReservation);
        Reservation reservation = this.consoleReservationRepo.findById(id);
        assertTrue(reservation.equals(this.testReservation));
    }

    @DisplayName("can return null for nonexistent id")
    @Test
    void can_return_null_for_nonexistent_id() {
        long nonexistentId = this.consoleReservationRepo.add(this.testReservation) + 1;
        Reservation nonexistent = this.consoleReservationRepo.findById(nonexistentId);
        assertNull(nonexistent);
    }

    @DisplayName("can count when date and time match")
    @Test
    void can_count_when_date_and_time_match() {
        int CYCLE = 17;
        for (int i = 0; i < CYCLE; i++) {
            this.consoleReservationRepo.add(this.testReservation);
        }
        int count = this.consoleReservationRepo.countWhenDateAndTimeMatch(
                Date.valueOf(this.testReservation.getDate()),
                Time.valueOf(this.testReservation.getTime()));
        assertEquals(CYCLE, count);
    }

    @DisplayName("can delete by id")
    @Test
    void can_delete_by_id() {
        long id = this.consoleReservationRepo.add(this.testReservation);
        int code = this.consoleReservationRepo.delete(id);
        assertEquals(1, code);

        Reservation reservation = consoleReservationRepo.findById(id);
        assertNull(reservation);
    }
}
