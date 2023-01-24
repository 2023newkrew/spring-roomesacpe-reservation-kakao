package nextstep.repository.reservation;

import nextstep.domain.Reservation;
import nextstep.exception.EscapeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static nextstep.exception.ErrorCode.DUPLICATED_RESERVATION_EXISTS;
import static nextstep.exception.ErrorCode.RESERVATION_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsoleReservationRepositoryTest {

    ConsoleReservationRepository consoleReservationRepository = new ConsoleReservationRepository();
    Reservation reservation = new Reservation(
            1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", 1L);

    @AfterEach
    void setUpTable() throws Exception {
        consoleReservationRepository.dropTable();
        consoleReservationRepository.createTable();
    }

    @Test
    void 예약을_저장할_수_있다() {
        assertDoesNotThrow(() -> consoleReservationRepository.save(reservation));
    }

    @Test
    void 중복된_일시에_예약할_수_없다() {
        EscapeException e = assertThrows(EscapeException.class, () -> {
            consoleReservationRepository.save(reservation);
            consoleReservationRepository.save(reservation);
        });
        assertThat(e.getErrorCode()).isEqualTo(DUPLICATED_RESERVATION_EXISTS);
    }

    @Test
    void 예약을_조회할_수_있다() {
        //given
        Long savedId = consoleReservationRepository.save(reservation);

        //when
        Reservation savedReservation = consoleReservationRepository.findById(savedId);

        //then
        assertThat(reservation.getName()).isEqualTo(savedReservation.getName());
        assertThat(reservation.getDate()).isEqualTo(savedReservation.getDate());
        assertThat(reservation.getThemeId()).isEqualTo(savedReservation.getThemeId());
    }

    @Test
    void 특정_테마ID를_가진_예약들을_조회할_수_있다() {
        //given
        Reservation reservation2 = new Reservation(
                2L, LocalDate.parse("2022-01-03"), LocalTime.parse("13:00"), "bryan", 1L);
        consoleReservationRepository.save(reservation);
        consoleReservationRepository.save(reservation2);

        //when
        List<Reservation> reservations = consoleReservationRepository.findByThemeId(1L);

        //then
        assertThat(reservations.size()).isEqualTo(2);
    }

    @Test
    void 없는_예약을_조회할_수_없다() {
        //given
        Long fakeId = 1L;

        //when, then
        EscapeException e = assertThrows(EscapeException.class,
                () -> consoleReservationRepository.findById(fakeId));

        assertThat(e.getErrorCode()).isEqualTo(RESERVATION_NOT_FOUND);
    }

    @Test
    void 예약을_취소할_수_있다() {
        //given
        Long savedId = consoleReservationRepository.save(reservation);

        //when
        consoleReservationRepository.deleteById(savedId);

        //then
        EscapeException e = assertThrows(EscapeException.class,
                () -> consoleReservationRepository.findById(savedId));
        assertThat(e.getErrorCode()).isEqualTo(RESERVATION_NOT_FOUND);
    }

    @Test
    void 없는_예약을_취소할_수_없다() {
        //given
        Long fakeId = 1L;

        //when, then
        EscapeException e = assertThrows(EscapeException.class,
                () -> consoleReservationRepository.deleteById(fakeId));
        assertThat(e.getErrorCode()).isEqualTo(RESERVATION_NOT_FOUND);
    }
}