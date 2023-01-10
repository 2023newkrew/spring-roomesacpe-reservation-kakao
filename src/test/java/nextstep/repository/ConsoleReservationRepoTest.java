package nextstep.repository;

import nextstep.Theme;
import nextstep.domain.reservation.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

public class ConsoleReservationRepoTest {
    private ConsoleReservationRepo consoleReservationRepo = new ConsoleReservationRepo();

    @DisplayName("add reservation test")
    @Test
    void addReservation() {
        Reservation reservation = new Reservation(
                LocalDate.parse("2022-08-11"),
                LocalTime.parse("13:00"),
                "name",
                new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000)
        );

        long id = consoleReservationRepo.add(reservation);

        assertThat(id).isEqualTo(1L);
    }

}
