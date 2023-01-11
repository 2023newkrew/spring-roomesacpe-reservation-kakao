package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ConsoleReservationRepositoryTest {

    ConsoleReservationRepository consoleReservationRepository = new ConsoleReservationRepository();
    static Theme theme;

    @BeforeAll
    static void setUpTheme() {
        theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
    }

    @AfterEach
    void setUpTable() throws Exception {
        consoleReservationRepository.dropTable();
        consoleReservationRepository.createTable();
    }

    @Test
    void 예약을_저장할_수_있다() {
        //given
        Reservation reservation = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme);

        //when, then
        assertDoesNotThrow(() -> consoleReservationRepository.save(reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                new Theme(
                        reservation.getTheme().getName(),
                        reservation.getTheme().getDesc(),
                        reservation.getTheme().getPrice())
        ));
    }

    @Test
    void 중복된_일시에_예약할_수_없다() {
        //given
        Reservation reservation = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme);
        Reservation duplicatedReservation = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme);

        consoleReservationRepository.save(reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                new Theme(
                        reservation.getTheme().getName(),
                        reservation.getTheme().getDesc(),
                        reservation.getTheme().getPrice())
        );
        //when, then
        assertThatThrownBy(() -> consoleReservationRepository.save(duplicatedReservation.getDate(),
                duplicatedReservation.getTime(),
                duplicatedReservation.getName(),
                new Theme(
                        duplicatedReservation.getTheme().getName(),
                        duplicatedReservation.getTheme().getDesc(),
                        duplicatedReservation.getTheme().getPrice())
        )).isInstanceOf(RuntimeException.class)
                .hasMessage("이미 예약이 존재합니다.");

    }
    @Test
    void 예약을_조회할_수_있다() {
        //given
        Reservation reservation = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme);

        Long savedId = consoleReservationRepository.save(reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                new Theme(
                        reservation.getTheme().getName(),
                        reservation.getTheme().getDesc(),
                        reservation.getTheme().getPrice())
        );

        //when
        Reservation reservation2 = consoleReservationRepository.findById(savedId);

        //then
        assertThat(reservation.getName()).isEqualTo(reservation2.getName());
        assertThat(reservation.getDate()).isEqualTo(reservation2.getDate());
        assertThat(reservation.getTheme().getName()).isEqualTo(reservation2.getTheme().getName());
    }

    @Test
    void 예약을_삭제할_수_있다() {
        //given
        Reservation reservation = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme);

        Long savedId = consoleReservationRepository.save(reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                new Theme(
                        reservation.getTheme().getName(),
                        reservation.getTheme().getDesc(),
                        reservation.getTheme().getPrice())
        );

        //when
        try {
            consoleReservationRepository.deleteById(savedId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //then
        assertThatThrownBy(() -> consoleReservationRepository.findById(savedId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("예약 내역을 찾을 수 없습니다.");

    }
}