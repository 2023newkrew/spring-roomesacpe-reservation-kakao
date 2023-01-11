package nextstep.domain.reservation.repository;

import nextstep.common.DatabaseExecutor;
import nextstep.domain.reservation.Reservation;
import nextstep.domain.theme.Theme;
import nextstep.domain.theme.repository.ThemeRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest(includeFilters = {
        @Filter(type = FilterType.ANNOTATION, classes = Repository.class),
        @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { DatabaseExecutor.class })
    }
)
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @BeforeAll
    static void createTable(@Autowired DatabaseExecutor databaseExecutor) {
        databaseExecutor.createReservationTable();
        databaseExecutor.createThemeTable();
    }

    @Test
    void 예약을_생성한다() {
        // given
        Theme theme = themeRepository.save(new Theme("베니스 상인의 저택", "테마 설명", 25_000));
        Reservation reservation = new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", theme);

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
        Theme theme = themeRepository.save(new Theme("베니스 상인의 저택", "테마 설명", 25_000));
        Reservation savedReservation = reservationRepository.save(new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", theme));
        Long id = savedReservation.getId();

        // when
        Reservation findById = reservationRepository.findById(id)
                .orElseThrow();

        // then
        assertThat(savedReservation).usingRecursiveComparison()
                .ignoringFields("theme.id")
                .isEqualTo(findById);
    }

    @Test
    void id에_해당하는_예약이_없을_경우_아무것도_반환되지_않는다() {
        // given
        Theme theme = themeRepository.save(new Theme("베니스 상인의 저택", "테마 설명", 25_000));
        Reservation savedReservation = reservationRepository.save(new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", theme));
        Long invalidId = savedReservation.getId() + 1000;

        // when
        Optional<Reservation> findById = reservationRepository.findById(invalidId);

        // then
        assertThat(findById).isEqualTo(Optional.empty());
    }

    @Test
    void 날짜와_시간이_같은_예약을_조회한다() {
        // given
        Theme theme = themeRepository.save(new Theme("베니스 상인의 저택", "테마 설명", 25_000));
        Reservation reservation = new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", theme);
        reservationRepository.save(reservation);

        // when, then
        assertThat(reservationRepository.existsByDateAndTime(reservation.getDate(), reservation.getTime())).isTrue();
    }

    @Test
    void id에_해당하는_예약을_삭제한다() {
        // given
        Theme theme = themeRepository.save(new Theme("베니스 상인의 저택", "테마 설명", 25_000));
        Reservation reservation = new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", theme);
        Long reservationId = reservationRepository.save(reservation).getId();

        // when, then
        assertThat(reservationRepository.deleteById(reservationId)).isTrue();
    }
}
