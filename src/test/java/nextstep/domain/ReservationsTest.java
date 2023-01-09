package nextstep.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationsTest {

    private static final Theme THEME = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    private Reservations reservations;

    @BeforeEach
    void setUp() {
        reservations = new Reservations();
    }

    @Test
    void 예약을_생성한다() {
        // given
        Reservation reservation = new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", THEME);

        // when
        Reservation savedReservation = reservations.save(reservation);

        // then
        assertThat(savedReservation).isNotNull();
        assertThat(savedReservation.getId()).isOne();
    }

    @Test
    void 예약을_id로_조회한다() {
        // given
        Reservation reservation = new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", THEME);
        Reservation savedReservation = reservations.save(reservation);
        Long id = savedReservation.getId();

        // when
        Reservation findById = reservations.findById(id).get();

        // then
        assertThat(savedReservation).usingRecursiveComparison()
                .isEqualTo(findById);
    }

    @Test
    void id에_해당하는_예약이_없을_경우_아무것도_반환되지_않는다() {
        // given
        Reservation reservation = new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", THEME);
        Reservation savedReservation = reservations.save(reservation);
        Long invalidId = savedReservation.getId() + 1000;

        // when
        Optional<Reservation> findById = reservations.findById(invalidId);

        // then
        assertThat(findById).isEqualTo(Optional.empty());
    }
}
