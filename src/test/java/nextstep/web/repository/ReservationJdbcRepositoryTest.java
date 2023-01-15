package nextstep.web.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.web.exception.BusinessException;
import nextstep.web.repository.database.ReservationJdbcRepository;
import nextstep.web.repository.database.ThemeJdbcRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;

@JdbcTest
class ReservationJdbcRepositoryTest {

    ThemeRepository themeRepository;

    ReservationJdbcRepository reservationJdbcRepository;

    Reservation reservation;

    Theme theme;

    @Autowired
    public ReservationJdbcRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.themeRepository = new ThemeJdbcRepository(jdbcTemplate);
        this.reservationJdbcRepository = new ReservationJdbcRepository(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        theme = new Theme(1L, "워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
        themeRepository.save(theme);
        reservation = Reservation.builder()
                .date(LocalDate.of(2023, 1, 10))
                .time(LocalTime.MIDNIGHT)
                .name("베인")
                .theme(theme)
                .build();
    }

    @Test
    void 예약을_저장후_조회할_수_있다() {
        Long createdId = reservationJdbcRepository.save(reservation);

        Assertions.assertThat(reservationJdbcRepository.findById(createdId)
                        .getId())
                .isEqualTo(createdId);
        Assertions.assertThat(reservationJdbcRepository.findById(createdId)
                        .getName())
                .isEqualTo(reservation.getName());
    }

    @Test
    void 예약후_취소할_수_있다() {
        Long createdId = reservationJdbcRepository.save(reservation);
        reservationJdbcRepository.deleteById(createdId);

        Assertions.assertThatThrownBy(() -> reservationJdbcRepository.findById(createdId))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 없는_예약을_취소하면_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> reservationJdbcRepository.deleteById(1L))
                .isInstanceOf(BusinessException.class);
    }
}
