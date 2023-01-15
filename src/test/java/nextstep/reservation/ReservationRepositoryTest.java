package nextstep.reservation;

import nextstep.reservation.entity.Reservation;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.repository.ReservationRepository;
import nextstep.reservation.repository.ThemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ThemeRepository themeRepository;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        Theme theme = new Theme(null, "워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        themeRepository.save(theme);

        this.reservation = new Reservation(null, LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name", 1L);
    }

    @Test
    @DisplayName("예약 삽입 성공")
    void createReservationTest() {

        //given

        //when
        Reservation created = reservationRepository.save(reservation);

        //then
        assertThat(reservationDataEquals(created, reservation)).isTrue();
    }

    @Test
    @DisplayName("예약 ID로 조회 성공")
    void findByIdTest() {
        //given
        Reservation created = reservationRepository.save(reservation);

        //when
        Optional<Reservation> result = reservationRepository.findById(created.getId());

        //then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(created);
    }

    @Test
    @DisplayName("날짜/시간으로 예약 존재 여부 조회(예약 있을 때)")
    void findByDateTimeTest() {
        //given
        reservationRepository.save(reservation);

        //when
        List<Reservation> result = reservationRepository.findByDateAndTime(reservation.getDate(), reservation.getTime());

        //then
        assertThat(reservationDataEquals(reservation, result.get(0))).isTrue();
    }

    @Test
    @DisplayName("날짜/시간으로 예약 존재 여부 조회(예약 없을 때")
    void findByDateTimeEmptyTest() {
        //given

        //when
        List<Reservation> result = reservationRepository.findByDateAndTime(LocalDate.parse("2022-08-14"), LocalTime.parse("13:00"));

        //then
        assertThat(result).isEqualTo(List.of());
    }

    @Test
    @DisplayName("예약 삭제 성공")
    void deleteReservation() {

        //given
        Reservation created = reservationRepository.save(reservation);

        //when
        int result = reservationRepository.deleteById(created.getId());

        //then
        assertThat(result).isEqualTo(1);

        Optional<Reservation> deletedReservationOptional = reservationRepository.findById(created.getId());
        assertThat(deletedReservationOptional.isPresent()).isFalse();
    }

    private boolean reservationDataEquals(Reservation a, Reservation b) {
        return a.getName().equals(b.getName()) &&
                a.getDate().equals(b.getDate()) &&
                a.getTime().equals(b.getTime()) &&
                a.getThemeId().equals(b.getThemeId());
    }
}