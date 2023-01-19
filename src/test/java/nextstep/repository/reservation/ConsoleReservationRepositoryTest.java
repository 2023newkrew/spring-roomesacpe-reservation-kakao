package nextstep.repository.reservation;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.repository.ResetTable;
import nextstep.repository.theme.ConsoleThemeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ConsoleReservationRepositoryTest {

    ConsoleReservationRepository consoleReservationRepository = new ConsoleReservationRepository();
    ConsoleThemeRepository consoleThemeRepository = new ConsoleThemeRepository();
    static Theme theme;
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    ResetTable resetTable = new ResetTable(jdbcTemplate);

    @BeforeAll
    static void setUpTheme() {
        theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
    }

    @BeforeEach
    void setUp() {
        resetTable.consoleReservationReset();
        resetTable.consoleThemeTableReset();
        consoleThemeRepository.save(theme);
        theme = consoleThemeRepository.findByTheme(theme);
    }

    @AfterEach
    void setUpTable() {
        resetTable.consoleReservationReset();
        resetTable.consoleThemeTableReset();
    }

    @Test
    void 예약을_저장할_수_있다() {
        //given
        Reservation reservation = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme.getId());

        //when, then
        assertDoesNotThrow(() -> consoleReservationRepository.save(reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                theme
        ));
    }

    @Test
    void 중복된_일시에_예약할_수_없다() {
        //given
        Reservation reservation = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme.getId());
        Reservation duplicatedReservation = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme.getId());

        consoleReservationRepository.save(reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                theme
        );
        //when, then
        assertThatThrownBy(() -> consoleReservationRepository.save(reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                theme
        )).isInstanceOf(RuntimeException.class)
                .hasMessage("이미 예약이 존재합니다.");

    }

    @Test
    void 예약을_조회할_수_있다() {
        //given
        Reservation reservation = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme.getId());

        Long savedId = consoleReservationRepository.save(reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                theme
        );

        //when
        Reservation reservation2 = consoleReservationRepository.findById(savedId);

        //then
        assertThat(reservation.getName()).isEqualTo(reservation2.getName());
        assertThat(reservation.getDate()).isEqualTo(reservation2.getDate());
    }

    @Test
    void 예약을_삭제할_수_있다() {
        //given
        Reservation reservation = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme.getId());

        Long savedId = consoleReservationRepository.save(reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                theme
        );

        //when
        consoleReservationRepository.deleteById(savedId);

        //then
        assertThatThrownBy(() -> consoleReservationRepository.findById(savedId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("예약 내역을 찾을 수 없습니다.");

    }
}