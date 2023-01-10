package nextstep.domain.repository;

import nextstep.common.DatabaseExecutor;
import nextstep.domain.reservation.Reservation;
import nextstep.domain.theme.Theme;
import nextstep.domain.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static nextstep.domain.QuerySetting.Reservation.*;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest(includeFilters = {
        @Filter(type = FilterType.ANNOTATION, classes = Repository.class),
        @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { DatabaseExecutor.class })
    }
)
public class ReservationRepositoryTest {

    private static final Theme DEFAULT_THEME = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeAll
    static void createTable(@Autowired DatabaseExecutor databaseExecutor) {
        databaseExecutor.createTable(TABLE_NAME);
    }

    @Test
    void 예약을_생성한다() {
        // given
        Reservation reservation = new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", DEFAULT_THEME);

        // when
        Reservation savedReservation = reservationRepository.save(reservation);

        // then
        assertThat(savedReservation.getId()).isNotNull();
        assertThat(savedReservation).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(reservation);
    }

    @Test
    void 예약을_id로_조회한다() {
        // given
        Reservation reservation = new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", DEFAULT_THEME);
        Reservation savedReservation = reservationRepository.save(reservation);
        Long id = savedReservation.getId();

        // when
        Reservation findById = reservationRepository.findById(id)
                .orElseThrow();

        // then
        assertThat(savedReservation).usingRecursiveComparison()
                .isEqualTo(findById);
    }

    @Test
    void id에_해당하는_예약이_없을_경우_아무것도_반환되지_않는다() {
        // given
        Reservation reservation = new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", DEFAULT_THEME);
        Reservation savedReservation = reservationRepository.save(reservation);
        Long invalidId = savedReservation.getId() + 1000;

        // when
        Optional<Reservation> findById = reservationRepository.findById(invalidId);

        // then
        assertThat(findById).isEqualTo(Optional.empty());
    }

    @Test
    void 날짜와_시간이_같은_예약을_조회한다() {
        // given
        Reservation reservation = new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", DEFAULT_THEME);
        reservationRepository.save(reservation);

        // when, then
        assertThat(reservationRepository.existsByDateAndTime(reservation.getDate(), reservation.getTime())).isTrue();
    }

    @Test
    void id에_해당하는_예약을_삭제한다() {
        // given
        Reservation reservation = new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", DEFAULT_THEME);
        Long reservationId = reservationRepository.save(reservation).getId();

        // when, then
        assertThat(reservationRepository.deleteById(reservationId)).isTrue();
    }
}
