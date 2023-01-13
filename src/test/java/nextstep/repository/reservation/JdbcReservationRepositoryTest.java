package nextstep.repository.reservation;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.exception.ReservationException;
import nextstep.repository.reservation.JdbcReservationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static nextstep.exception.ErrorCode.RESERVATION_NOT_FOUND;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class JdbcReservationRepositoryTest {

    @Autowired
    JdbcReservationRepository jdbcReservationRepository;

    static Theme theme;

    @BeforeAll
    static void setUpTheme() {
        theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
    }

    @AfterEach
    void setUp() {
        jdbcReservationRepository.dropTable();
        jdbcReservationRepository.createTable();
    }

    @Test
    void 예약을_저장할_수_있다() {
        //given
        Reservation reservation = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme);

        //when, then
        assertDoesNotThrow(() -> jdbcReservationRepository.save(reservation));
    }

    @Test
    void 중복된_일시에_예약할_수_없다() {
        //given
        Reservation reservation = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme);

        //when, then
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> {
                    jdbcReservationRepository.save(reservation);
                    jdbcReservationRepository.save(reservation);
                }
        );
    }

    @Test
    void 예약을_조회할_수_있다() {
        //given
        Reservation reservation = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme);

        Long saveId = jdbcReservationRepository.save(reservation);

        //when
        Reservation foundReservation = jdbcReservationRepository.findById(saveId);

        //then
        assertThat(reservation.getName()).isEqualTo(foundReservation.getName());
        assertThat(reservation.getDate()).isEqualTo(foundReservation.getDate());
        assertThat(reservation.getTheme().getName()).isEqualTo(foundReservation.getTheme().getName());
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
        Reservation reservation = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme);

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