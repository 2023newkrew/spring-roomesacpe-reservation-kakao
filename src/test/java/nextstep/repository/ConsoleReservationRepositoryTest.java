package nextstep.repository;

import nextstep.domain.theme.Theme;
import nextstep.domain.reservation.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@DisplayName("Reservation Repository 테스트 (콘솔)")
@Sql(scripts = {"classpath:recreate.sql"})
public class ConsoleReservationRepositoryTest {
    private final ReservationRepository consoleReservationRepository = new ConsoleReservationRepository();
    private static final Reservation newReservation = new Reservation(
            null,
            LocalDate.parse("2022-08-11"),
            LocalTime.parse("13:00"),
            "name",
            new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000)
    );

    @DisplayName("insert 후 예약 값이 잘 들어갔는지 insert 전과 값 비교")
    @Test
    void consoleReservationRepo() {
        // 추가
        long id = consoleReservationRepository.add(newReservation);
        Reservation reservation = consoleReservationRepository.findById(id).orElseThrow();
        assertThat(reservation).isEqualTo(newReservation);
    }

    @DisplayName("예약 시간이 같은 예약의 개수 카운트")
    @Test
    void insertDuplicateReservation() {
        consoleReservationRepository.add(newReservation);
        int countSameDateAndTime = consoleReservationRepository.countByDateAndTime(
                Date.valueOf(newReservation.getDate()),
                Time.valueOf(newReservation.getTime()));
        assertThat(countSameDateAndTime).isEqualTo(1);
    }

    @DisplayName("예약 삭제 후 조회 불가 확인")
    @Test
    void deleteReservation() {
        long id = consoleReservationRepository.add(newReservation);
        // 삭제
        consoleReservationRepository.delete(id);
        Reservation reservation2 = consoleReservationRepository.findById(id).orElse(null);
        assertThat(reservation2).isNull();
    }
}
