package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Transactional
class JdbcReservationRepositoryTest {

    @Autowired JdbcReservationRepository jdbcReservationRepository;

    static Theme theme;

    @BeforeAll
    static void setUpTheme() {
        theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
    }

    @AfterEach
    void setUp() throws Exception {
        jdbcReservationRepository.dropTable();
        jdbcReservationRepository.createTable();
    }

    @DisplayName("예약을 저장할 수 있다.")
    @Test
    void jdbcAddReservationTest() {
        Reservation actual = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme);

        assertDoesNotThrow(() -> jdbcReservationRepository.save(actual.getDate(),
                actual.getTime(),
                actual.getName(),
                new Theme(
                        actual.getTheme().getName(),
                        actual.getTheme().getDesc(),
                        actual.getTheme().getPrice())
        ));
    }

    @DisplayName("중복 예약은 예약할 수 없다.")
    @Test
    void jdbcAddReservationExceptionTest() {
        Reservation actual = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme);

        assertDoesNotThrow(() -> jdbcReservationRepository.save(actual.getDate(),
                actual.getTime(),
                actual.getName(),
                new Theme(
                        actual.getTheme().getName(),
                        actual.getTheme().getDesc(),
                        actual.getTheme().getPrice())
        ));

        assertThatExceptionOfType(Exception.class).isThrownBy(() ->
                jdbcReservationRepository.save(actual.getDate(),
                        actual.getTime(),
                        actual.getName(),
                        new Theme(
                                actual.getTheme().getName(),
                                actual.getTheme().getDesc(),
                                actual.getTheme().getPrice()))
        );
    }

    @DisplayName("예약을 조회할 수 있다.")
    @Test
    void jdbcFindReservationTest() {
        //given
        Reservation actual = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme);

        Long saveId = jdbcReservationRepository.save(actual.getDate(),
                actual.getTime(),
                actual.getName(),
                new Theme(
                        actual.getTheme().getName(),
                        actual.getTheme().getDesc(),
                        actual.getTheme().getPrice())
        );

        //when
        Reservation reservation2 = jdbcReservationRepository.findById(saveId);

        //then
        assertThat(actual.getName()).isEqualTo(reservation2.getName());
        assertThat(actual.getDate()).isEqualTo(reservation2.getDate());
        assertThat(actual.getTheme().getName()).isEqualTo(reservation2.getTheme().getName());
    }

    @DisplayName("예약을 삭제할 수 있다.")
    @Test
    void jdbcDeleteReservationTest() {
        //given
        Reservation actual = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme);

        Long saveId = jdbcReservationRepository.save(actual.getDate(),
                actual.getTime(),
                actual.getName(),
                new Theme(
                        actual.getTheme().getName(),
                        actual.getTheme().getDesc(),
                        actual.getTheme().getPrice())
        );

        assertDoesNotThrow(() ->
                jdbcReservationRepository.deleteById(saveId)
        );

        assertThatExceptionOfType(Exception.class).isThrownBy(() ->
                jdbcReservationRepository.findById(saveId)
        );
    }

}