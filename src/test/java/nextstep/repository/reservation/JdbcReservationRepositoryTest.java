package nextstep.repository.reservation;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.exception.ReservationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static nextstep.exception.ErrorCode.RESERVATION_NOT_FOUND;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class JdbcReservationRepositoryTest {

    @Autowired
    JdbcReservationRepository jdbcReservationRepository;

    Theme theme = new Theme(4L, "테스트 테마", "테스트용 테마임", 1234);
    Reservation reservation = new Reservation(
            1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme.getId());

    @AfterEach
    void setUp() {
        jdbcReservationRepository.dropTable();
        jdbcReservationRepository.createTable();
    }

    @Test
    void 예약을_저장할_수_있다() {
        assertDoesNotThrow(() -> jdbcReservationRepository.save(reservation));
    }

    @Test
    void 중복된_일시에_예약할_수_없다() {
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> {
                    jdbcReservationRepository.save(reservation);
                    jdbcReservationRepository.save(reservation);
                }
        );
    }

    @Test
    void 예약을_조회할_수_있다() {
        //given
        Long saveId = jdbcReservationRepository.save(reservation);

        //when
        Reservation foundReservation = jdbcReservationRepository.findById(saveId);

        //then
        assertThat(reservation.getName()).isEqualTo(foundReservation.getName());
        assertThat(reservation.getDate()).isEqualTo(foundReservation.getDate());
        assertThat(reservation.getThemeId()).isEqualTo(foundReservation.getThemeId());
    }

    @Test
    void 특정_테마ID를_가진_예약들을_조회할_수_있다() {
        //given
        Reservation reservation2 = new Reservation(
                2L, LocalDate.parse("2022-01-03"), LocalTime.parse("13:00"), "bryan", theme.getId());
        jdbcReservationRepository.save(reservation);
        jdbcReservationRepository.save(reservation2);

        //when
        List<Reservation> foundReservations = jdbcReservationRepository.findByThemeId(theme.getId());

        //then
        assertThat(foundReservations.size()).isEqualTo(2);
    }

    @Test
    void 없는_예약을_조회할_수_없다() {
        //given
        Long fakeId = 1L;

        //when, then
        ReservationException e = assertThrows(ReservationException.class,
                () -> jdbcReservationRepository.findById(fakeId));
        assertThat(e.getErrorCode()).isEqualTo(RESERVATION_NOT_FOUND);
    }

    @Test
    void 예약을_취소할_수_있다() {
        //given
        Long saveId = jdbcReservationRepository.save(reservation);

        //when, then
        assertDoesNotThrow(() -> jdbcReservationRepository.deleteById(saveId));
        assertThatThrownBy(() -> jdbcReservationRepository.findById(saveId))
                .isInstanceOf(Exception.class);
    }

    @Test
    void 없는_예약을_취소할_수_없다() {
        //given
        Long fakeId = 1L;

        //when, then
        ReservationException e = assertThrows(ReservationException.class,
                () -> jdbcReservationRepository.deleteById(fakeId));
        assertThat(e.getErrorCode()).isEqualTo(RESERVATION_NOT_FOUND);
    }

}