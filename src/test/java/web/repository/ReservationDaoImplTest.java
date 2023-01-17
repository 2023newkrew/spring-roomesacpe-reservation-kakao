package web.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import web.entity.Reservation;
import web.exception.ReservationDuplicateException;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReservationDaoImplTest {

    DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:schema.sql")
            .build();

    private ReservationDaoImpl reservationDaoImpl = new ReservationDaoImpl(dataSource);

    @Nested
    class Save {

        @Test
        void should_successfully_when_validReservation() {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
            String name = "name";
            Reservation reservation = Reservation.of(today, now, name);

            long reservationId = reservationDaoImpl.save(reservation);

            Reservation savedReservation = reservationDaoImpl.findById(reservationId).orElseThrow();
            assertThat(savedReservation.getDate()).isEqualTo(today);
            assertThat(savedReservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).isEqualTo(now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            assertThat(savedReservation.getName()).isEqualTo(name);
        }

        @Test
        void should_throwException_when_saveDuplicateReservation() {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
            String name = "name";
            Reservation reservation = Reservation.of(today, now, name);

            reservationDaoImpl.save(reservation);

            assertThatThrownBy(() -> reservationDaoImpl.save(reservation))
                    .isInstanceOf(ReservationDuplicateException.class);
        }
    }

    @Nested
    class Find {

        @Test
        void should_successfully_when_validReservationId() {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
            String name = "name";
            Reservation reservation = Reservation.of(today, now, name);
            long reservationId = reservationDaoImpl.save(reservation);

            Reservation findReservation = reservationDaoImpl.findById(reservationId)
                    .orElseThrow();

            assertThat(findReservation.getDate()).isEqualTo(today);
            assertThat(findReservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).isEqualTo(now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            assertThat(findReservation.getName()).isEqualTo(name);
        }

        @Test
        void should_throwException_when_notExistReservation() {
            assertThat(reservationDaoImpl.findById(-1)).isEmpty();
        }
    }

    @Nested
    class Cancel {

        @Test
        void should_successfully_when_validReservationId() {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
            String name = "name";
            Reservation reservation = Reservation.of(today, now, name);
            long reservationId = reservationDaoImpl.save(reservation);

            long deleteReservationCount = reservationDaoImpl.delete(reservationId);
            assertThat(deleteReservationCount).isEqualTo(1L);
            assertThat(reservationDaoImpl.findById(reservationId)).isEmpty();
        }

        @Test
        void should_throwException_when_notExistReservation() {
            assertThat(reservationDaoImpl.delete(-1)).isEqualTo(0L);
        }
    }

    @AfterEach
    void deleteAllReservation() {
        reservationDaoImpl.clearAll();
    }
}
