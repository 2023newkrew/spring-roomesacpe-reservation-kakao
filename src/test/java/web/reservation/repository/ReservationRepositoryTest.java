package web.reservation.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import web.entity.Reservation;
import web.entity.Theme;
import web.reservation.exception.ReservationException;
import web.theme.repository.ThemeRepository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReservationRepositoryTest {

    DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:schema.sql")
            .build();

    private ThemeRepository themeRepository = new ThemeRepository(dataSource);
    private ReservationRepository reservationRepository = new DatabaseReservationRepository(dataSource, themeRepository);

    private long savedThemeId;

    @BeforeEach
    void saveDummyTheme() {
        String name = "테마이름";
        String desc = "테마설명";
        int price = 22000;
        Theme theme = Theme.of(null, name, desc, price);

        savedThemeId = themeRepository.save(theme);
    }

    @AfterEach
    void clearDummyTheme() {
        themeRepository.delete(savedThemeId);
    }

    @AfterEach
    void deleteAllReservation() {
        reservationRepository.clearAll();
    }

    @Nested
    class Save {

        @Test
        void should_successfully_when_validReservation() {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
            String name = "name";
            Reservation reservation = Reservation.of(null, today, now, name, savedThemeId);

            long reservationId = reservationRepository.save(reservation);

            Reservation savedReservation = reservationRepository.findById(reservationId).orElseThrow();
            assertThat(savedReservation.getDate()).isEqualTo(today);
            assertThat(savedReservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).isEqualTo(now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            assertThat(savedReservation.getName()).isEqualTo(name);
        }

        @Test
        void should_throwException_when_saveDuplicateReservation() {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
            String name = "name";
            Reservation reservation = Reservation.of(null, today, now, name, savedThemeId);

            reservationRepository.save(reservation);

            assertThatThrownBy(() -> reservationRepository.save(reservation))
                    .isInstanceOf(ReservationException.class);
        }
    }

    @Nested
    class Find {

        @Test
        void should_successfully_when_existReservation() {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
            String name = "name";
            Reservation reservation = Reservation.of(null, today, now, name, savedThemeId);
            long reservationId = reservationRepository.save(reservation);

            Reservation findReservation = reservationRepository.findById(reservationId)
                    .orElseThrow();

            assertThat(findReservation.getDate()).isEqualTo(today);
            assertThat(findReservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).isEqualTo(now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            assertThat(findReservation.getName()).isEqualTo(name);
        }

        @Test
        void should_throwException_when_notExistReservation() {
            assertThat(reservationRepository.findById(-1)).isEmpty();
        }

        @Nested
        class FindByThemeId {

            @Test
            void should_returnList_when_multiReservation() {
                LocalDate today = LocalDate.now();
                LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
                String name = "name";
                Reservation reservation = Reservation.of(null, today, now, name, savedThemeId);
                long reservationId = reservationRepository.save(reservation);

                LocalDate tomorrow = LocalDate.now().plusDays(1);
                LocalTime now2 = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
                String name2 = "name";
                Reservation reservation2 = Reservation.of(null, tomorrow, now2, name2, savedThemeId);
                long reservationId2 = reservationRepository.save(reservation2);

                List<Reservation> findReservations = reservationRepository.findAllByThemeId(savedThemeId);

                assertThat(findReservations.get(0).getId()).isEqualTo(reservationId);
                assertThat(findReservations.get(0).getName()).isEqualTo(name);
                assertThat(findReservations.get(0).getDate()).isEqualTo(today);
                assertThat(findReservations.get(0).getTime()).isEqualTo(now);
                assertThat(findReservations.get(0).getThemeId()).isEqualTo(savedThemeId);
                assertThat(findReservations.get(1).getId()).isEqualTo(reservationId2);
                assertThat(findReservations.get(1).getName()).isEqualTo(name2);
                assertThat(findReservations.get(1).getDate()).isEqualTo(tomorrow);
                assertThat(findReservations.get(1).getTime()).isEqualTo(now2);
                assertThat(findReservations.get(1).getThemeId()).isEqualTo(savedThemeId);
            }

            @Test
            void should_returnEmptyList_when_notExistTheme() {
                List<Reservation> findReservations = reservationRepository.findAllByThemeId(-1L);

                assertThat(findReservations).isNotNull();
                assertThat(findReservations).isEmpty();
            }
        }
    }

    @Nested
    class Cancel {

        @Test
        void should_successfully_when_existReservation() {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
            String name = "name";
            Reservation reservation = Reservation.of(null, today, now, name, savedThemeId);
            long reservationId = reservationRepository.save(reservation);

            long deleteReservationCount = reservationRepository.delete(reservationId);
            assertThat(deleteReservationCount).isEqualTo(1L);
            assertThat(reservationRepository.findById(reservationId)).isEmpty();
        }

        @Test
        void should_return0_when_notExistReservation() {
            assertThat(reservationRepository.delete(-1)).isEqualTo(0L);
        }
    }
}
