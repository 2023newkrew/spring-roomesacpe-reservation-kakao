package web.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import web.entity.Reservation;
import web.exception.ReservationDuplicateException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static web.repository.MemoryReservationRepository.reservations;

public class ReservationRepositoryTest {

    private final MemoryReservationRepository reservationRepository = new MemoryReservationRepository();

    @Nested
    class Save {

        @Test
        void should_successfully_when_validReservation() {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();
            String name = "name";
            Reservation reservation = Reservation.of(today, now, name);

            long reservationId = reservationRepository.save(reservation);

            assertThat(reservations).hasSize(1);
            Reservation savedReservation = reservations.get(reservationId);
            assertThat(savedReservation.getDate()).isEqualTo(today);
            assertThat(savedReservation.getTime()).isEqualTo(now);
            assertThat(savedReservation.getName()).isEqualTo(name);
        }

        @Test
        void should_throwException_when_saveDuplicateReservation() {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();
            String name = "name";
            Reservation reservation = Reservation.of(today, now, name);

            reservationRepository.save(reservation);

            assertThatThrownBy(() -> reservationRepository.save(reservation))
                    .isInstanceOf(ReservationDuplicateException.class);
        }
    }

    @Nested
    class Find {

        @Test
        void should_successfully_when_validReservationId() {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();
            String name = "name";
            Reservation reservation = Reservation.of(today, now, name);
            long reservationId = reservationRepository.save(reservation);

            Reservation findReservation = reservationRepository.findById(reservationId)
                    .orElseThrow();

            assertThat(findReservation.getDate()).isEqualTo(today);
            assertThat(findReservation.getTime()).isEqualTo(now);
            assertThat(findReservation.getName()).isEqualTo(name);
        }

        @Test
        void should_throwException_when_notExistReservation() {
            assertThat(reservationRepository.findById(-1)).isEmpty();
        }
    }

    @Nested
    class Cancel {

        @Test
        void should_successfully_when_validReservationId() {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();
            String name = "name";
            Reservation reservation = Reservation.of(today, now, name);
            long reservationId = reservationRepository.save(reservation);

            long deleteReservationCount = reservationRepository.delete(reservationId);
            assertThat(deleteReservationCount).isEqualTo(1L);
            assertThat(reservationRepository.findById(reservationId)).isEmpty();
        }

        @Test
        void should_throwException_when_notExistReservation() {
            assertThat(reservationRepository.delete(-1)).isEqualTo(0L);
        }
    }

    @AfterEach
    void deleteAllReservation() {
        reservations.clear();
    }
}
