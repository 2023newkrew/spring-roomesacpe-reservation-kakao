package nextstep.repository;

import nextstep.domain.theme.Theme;
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
public class WebAppReservationRepositoryTest {
    private final ReservationRepository webAppReservationRepository;

    @Autowired
    public WebAppReservationRepositoryTest(WebAppReservationRepository webAppReservationRepository) {
        this.webAppReservationRepository = webAppReservationRepository;
    }

    @DisplayName("reservation test")
    @Test
    void webAppReservationRepo() {

        Reservation newReservation = new Reservation(
                LocalDate.parse("2022-08-11"),
                LocalTime.parse("13:00"),
                "name",
                new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000)
        );

        // 추가
        long id = webAppReservationRepository.add(newReservation);
        Reservation reservation = webAppReservationRepository.findById(id).orElseThrow();
        assertThat(reservation).isEqualTo(newReservation);

        // 예약 시간 중복
        int countSameDateAndTime = webAppReservationRepository.countByDateAndTime(
                Date.valueOf(reservation.getDate()),
                Time.valueOf(reservation.getTime()));
        assertThat(countSameDateAndTime > 0).isTrue();

        // 삭제
        webAppReservationRepository.delete(id);
        Reservation reservation2 = webAppReservationRepository.findById(id).orElse(null);
        assertThat(reservation2).isNull();
    }
}
