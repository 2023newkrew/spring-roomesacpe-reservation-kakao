package nextstep.repository;

import nextstep.domain.theme.Theme;
import nextstep.domain.reservation.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
public class ConsoleReservationRepositoryTest {
    private final ReservationRepo consoleReservationRepo = new ConsoleReservationRepository();

    @DisplayName("reservation test")
    @Test
    void consoleReservationRepo() {
        Reservation newReservation = new Reservation(
                LocalDate.parse("2022-08-11"),
                LocalTime.parse("13:00"),
                "name",
                new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000)
        );

        // 추가
        long id = consoleReservationRepo.add(newReservation);
        Reservation reservation = consoleReservationRepo.findById(id).orElseThrow();
        assertThat(reservation).isEqualTo(newReservation);

        // 예약 시간 중복
        int countSameDateAndTime = consoleReservationRepo.countByDateAndTime(
                Date.valueOf(reservation.getDate()),
                Time.valueOf(reservation.getTime()));
        assertThat(countSameDateAndTime > 0).isTrue();

        // 삭제
        consoleReservationRepo.delete(id);
        Reservation reservation2 = consoleReservationRepo.findById(id).orElse(null);
        assertThat(reservation2).isNull();
    }
}
