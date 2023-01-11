package nextstep.repository;

import nextstep.Theme;
import nextstep.domain.reservation.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class WebAppReservationRepoTest {
    @Autowired
    private ReservationRepo webAppReservationRepo;

    @DisplayName("reservation test")
    @Test
    void webAppReservationRepo() {
        webAppReservationRepo.reset();

        Reservation newReservation = new Reservation(
                LocalDate.parse("2022-08-11"),
                LocalTime.parse("13:00"),
                "name",
                new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000)
        );

        long id = webAppReservationRepo.add(newReservation);
        Reservation reservation = webAppReservationRepo.findById(id);
        assertThat(reservation).isEqualTo(newReservation);

        int countSameDateAndTime = webAppReservationRepo.countWhenDateAndTimeMatch(
                Date.valueOf(reservation.getDate()),
                Time.valueOf(reservation.getTime()));
        assertThat(countSameDateAndTime > 0).isTrue();

        webAppReservationRepo.delete(id);

        Reservation reservation2 = webAppReservationRepo.findById(id);

        assertThat(reservation2).isNull();
    }
}
