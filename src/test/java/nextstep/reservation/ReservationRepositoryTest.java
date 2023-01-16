package nextstep.reservation;

import nextstep.reservation.entity.Reservation;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.exception.RoomEscapeException;
import nextstep.reservation.repository.ReservationRepository;
import nextstep.reservation.repository.ThemeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static nextstep.reservation.constant.RoomEscapeConstant.DUMMY_ID;
import static nextstep.reservation.exception.RoomEscapeExceptionCode.NO_SUCH_THEME;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ThemeRepository themeRepository;

    @AfterEach
    void tearDown() {
        themeRepository.clear();
    }

    @Test
    @DisplayName("예약 삽입 성공")
    void createReservationTest() {

        //given
        Theme theme = new Theme(DUMMY_ID, "워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        Theme savedTheme = themeRepository.save(theme);

        Reservation reservation = new Reservation(DUMMY_ID, LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name", savedTheme.getId());

        //when
        Reservation created = reservationRepository.save(reservation);

        //then
        assertThat(reservationDataEquals(created, reservation)).isTrue();
    }

    @Test
    @DisplayName("예약 생성 실패(테마가 존재하지 않는 경우)")
    void deleteReservationFail() {

        //given
        Reservation invalidReservation = new Reservation(DUMMY_ID, LocalDate.parse("2023-01-16"), LocalTime.parse("19:20"), "herbi", 1234L);

        //when
        //then
        Assertions.assertThatThrownBy(() -> reservationRepository.save(invalidReservation))
                .isInstanceOf(RoomEscapeException.class)
                .hasMessage(NO_SUCH_THEME.getMessage());
    }

    @Test
    @DisplayName("예약 ID로 조회 성공")
    void findByIdTest() {
        //given
        Theme theme = new Theme(DUMMY_ID, "워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        Theme savedTheme = themeRepository.save(theme);

        Reservation reservation = new Reservation(DUMMY_ID, LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name", savedTheme.getId());
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
        Theme theme = new Theme(DUMMY_ID, "워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        Theme savedTheme = themeRepository.save(theme);

        Reservation reservation = new Reservation(DUMMY_ID, LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name", savedTheme.getId());
        reservationRepository.save(reservation);

        //when
        List<Reservation> result = reservationRepository.findByDateAndTime(reservation.getDate(), reservation.getTime());

        //then
        assertThat(reservationDataEquals(reservation, result.get(0))).isTrue();
    }

    @Test
    @DisplayName("날짜/시간으로 예약 존재 여부 조회(예약 없을 때)")
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
        Theme theme = new Theme(DUMMY_ID, "워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        Theme savedTheme = themeRepository.save(theme);

        Reservation reservation = new Reservation(DUMMY_ID, LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name", savedTheme.getId());
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