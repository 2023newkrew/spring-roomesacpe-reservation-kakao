package nextstep.reservation.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import javax.sql.DataSource;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.entity.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class ReservationJdbcTemplateRepositoryTest {

    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private DataSource dataSource;
    private ReservationJdbcTemplateRepository repository;
    private final Reservation testReservation = Reservation.builder()
            .date(LocalDate.of(1982, 2, 19))
            .time(LocalTime.of(2, 2))
            .name("name")
            .theme(new Theme("워너고홈 ", "병맛 어드벤처 회사 코믹물", 29000))
            .build();

    @BeforeEach
    void setUp() {
        repository = new ReservationJdbcTemplateRepository(jdbcTemplate, dataSource);
    }

    @Test
    void 예약을_진행하면_아이디가_반환됩니다() {
        // when
        assertThat(testReservation.getId()).isNull();

        // given & then
        assertThat(repository.add(testReservation)).isInstanceOf(Long.class);
    }

    @Test
    void 예약조회는_Optional_객체를_반환합니다() {
        // when & given
        Long id = repository.add(testReservation);

        // then
        assertThat(repository.findById(id)).isInstanceOf(Optional.class);
    }

    @Test
    void 없는_예약조회시_Empty_Optional을_반환합니다() {
        assertThat(repository.findById(1L).isEmpty()).isTrue();
    }

    @Test
    void 전체_예약을_가져올_수_있습니다() {
        // when
        repository.add(Reservation.builder()
                .date(LocalDate.of(1982, 2, 19))
                .time(LocalTime.of(2, 2))
                .name("name")
                .theme(new Theme("워너고홈 ", "병맛 어드벤처 회사 코믹물", 29000))
                .build());
        repository.add(Reservation.builder()
                .date(LocalDate.of(1982, 2, 19))
                .time(LocalTime.of(2, 3))
                .name("name")
                .theme(new Theme("워너고홈 ", "병맛 어드벤처 회사 코믹물", 29000))
                .build());
        repository.add(Reservation.builder()
                .date(LocalDate.of(1982, 2, 19))
                .time(LocalTime.of(2, 4))
                .name("name")
                .theme(new Theme("워너고홈 ", "병맛 어드벤처 회사 코믹물", 29000))
                .build());

        // given & then
        assertThat(repository.findAll().size()).isEqualTo(3);
    }

    @Test
    void 예약을_삭제할_수_있습니다() {
        // when
        Long id = repository.add(testReservation);

        // given
        repository.delete(id);

        // then
        assertThat(repository.findById(testReservation.getId()).isEmpty()).isTrue();

    }
}