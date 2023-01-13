package nextstep.repository.reservation;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.exception.ReservationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static nextstep.exception.ErrorCode.DUPLICATED_RESERVATION_EXISTS;
import static nextstep.exception.ErrorCode.RESERVATION_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsoleReservationRepositoryTest {

    ConsoleReservationRepository consoleReservationRepository = new ConsoleReservationRepository();
    Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
    Reservation reservation = new Reservation(
            1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme);

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
        ReservationException e = assertThrows(ReservationException.class, () -> {
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
        Reservation foundReservation = consoleReservationRepository.findById(savedId);

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
        ReservationException e = assertThrows(ReservationException.class,
                () -> consoleReservationRepository.findById(savedId));
        assertThat(e.getErrorCode()).isEqualTo(RESERVATION_NOT_FOUND);
    }

    @Test
    void 없는_예약을_취소할_수_없다() {
        //given
        Long fakeId = 1L;

        //when, then
        ReservationException e = assertThrows(ReservationException.class,
                () -> consoleReservationRepository.deleteById(fakeId));
        assertThat(e.getErrorCode()).isEqualTo(RESERVATION_NOT_FOUND);
    }
}