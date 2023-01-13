package kakao.repository.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import kakao.domain.Reservation;
import kakao.domain.Theme;
import kakao.repository.theme.ThemeRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("예약 도메인 레포지토리 레이어 테스트")
class ReservationRepositoryTest {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ThemeRepository themeRepository;

    @Test
    @DisplayName("예약 레코드를 생성하고 조회할 수 있다.")
    public void testCreateReservation() {
        // given
        Theme theme = themeRepository.findById(1L);
        Reservation newReservation = Reservation.builder()
                .date(LocalDate.of(2023,1,12))
                .time(LocalTime.of(16,0))
                .name("test")
                .theme(theme)
                .build();

        // when
        Reservation reservation = reservationRepository.save(newReservation);

        //then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(reservation).isSameAs(newReservation);
        Reservation foundReservation = reservationRepository.findById(reservation.getId());
        softly.assertThat(foundReservation).isEqualTo(reservation);
    }

}