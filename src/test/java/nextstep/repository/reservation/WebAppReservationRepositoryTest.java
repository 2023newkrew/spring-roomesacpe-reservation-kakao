package nextstep.repository.reservation;

import nextstep.domain.theme.Theme;
import nextstep.domain.reservation.Reservation;
import nextstep.repository.reservation.ReservationRepository;
import nextstep.repository.reservation.WebAppReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DisplayName("Reservation Repository 테스트 (웹)")
@Sql(scripts = {"classpath:recreate.sql"})
public class WebAppReservationRepositoryTest {
    private final ReservationRepository webAppReservationRepository;

    @Autowired
    public WebAppReservationRepositoryTest(WebAppReservationRepository webAppReservationRepository) {
        this.webAppReservationRepository = webAppReservationRepository;
    }

    private static final Reservation newReservation = new Reservation(
            null,
            LocalDate.parse("2022-08-11"),
            LocalTime.parse("13:00:00"),
            "name",
            new Theme(1l, "워너고홈", "병맛 어드벤처 회사 코믹물", 29_000)
    );

    @DisplayName("insert 후 예약 값이 잘 들어갔는지 insert 전과 값 비교")
    @Test
    void addReservation() {
        // 추가
        long id = webAppReservationRepository.add(newReservation);
        Reservation reservation = webAppReservationRepository.findById(id).orElseThrow();
        assertThat(reservation).isEqualTo(newReservation);
    }

    @DisplayName("예약 시간이 같은 예약의 개수 카운트")
    @Test
    void insertDuplicateReservation() {
        webAppReservationRepository.add(newReservation);
        int countSameDateAndTime = webAppReservationRepository.countByDateAndTime(
                Date.valueOf(newReservation.getDate()),
                Time.valueOf(newReservation.getTime()));
        assertThat(countSameDateAndTime).isEqualTo(1);
    }

    @DisplayName("예약 삭제 후 조회 불가 확인")
    @Test
    void deleteReservation() {
        long id = webAppReservationRepository.add(newReservation);
        // 삭제
        webAppReservationRepository.delete(id);
        Reservation reservation2 = webAppReservationRepository.findById(id).orElse(null);
        assertThat(reservation2).isNull();
    }
}
