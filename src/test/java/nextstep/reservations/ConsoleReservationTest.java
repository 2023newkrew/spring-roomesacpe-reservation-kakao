package nextstep.reservations;

import nextstep.reservations.domain.entity.reservation.Reservation;
import nextstep.reservations.domain.entity.theme.Theme;
import nextstep.reservations.exceptions.reservation.exception.DuplicateReservationException;
import nextstep.reservations.exceptions.reservation.exception.NoSuchReservationException;
import nextstep.reservations.exceptions.theme.exception.NoSuchThemeException;
import nextstep.reservations.repository.reservation.JdbcReservationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsoleReservationTest {

    JdbcReservationRepository reservationRepository;

    Theme theme;

    @BeforeEach
    void setUp() {
        reservationRepository = new JdbcReservationRepository();
        reservationRepository.dropAndCreateTable();
        reservationRepository.initData();

        theme = Theme.builder()
                .name("워너고홈")
                .desc("병맛 어드벤처 회사 코믹물")
                .price(29_000)
                .build();
    }

    @Test
    void 예약_생성() {
        Reservation reservation = new Reservation(
                3L,
                LocalDate.parse("2022-07-01"),
                LocalTime.parse( "13:00"),
                "name",
                theme
        );

        Long reservationId = reservationRepository.add(reservation);

        assertThat(reservationId).isEqualTo(3L);
    }

    @Test
    void 예약_조회() {
        Reservation reservation = reservationRepository.findById(1L);
        assertThat(reservation.getId()).isEqualTo(1L);
        assertThat(reservation.getDate()).isEqualTo("2022-08-02");
        assertThat(reservation.getTime()).isEqualTo("13:00");
        assertThat(reservation.getName()).isEqualTo("name");
        assertThat(reservation.getTheme().getName()).isEqualTo("워너고홈");
        assertThat(reservation.getTheme().getDesc()).isEqualTo("병맛 어드벤처 회사 코믹물");
        assertThat(reservation.getTheme().getPrice()).isEqualTo(29_000);
    }

    @Test
    void 예약_삭제() {
        int removeCount = reservationRepository.remove(1L);
        assertThat(removeCount).isEqualTo(1L);
    }

    @Test
    void 중복_예약_오류() {
        Reservation reservation = new Reservation(
                3L,
                LocalDate.parse("1982-02-19"),
                LocalTime.parse( "13:00"),
                "name",
                theme
        );

        Assertions.assertThatThrownBy(() -> reservationRepository.add(reservation))
                .isInstanceOf(DuplicateReservationException.class);
    }

    @Test
    void 존재하지_않는_테마_예약_오류() {
        Reservation reservation = new Reservation(
                3L,
                LocalDate.parse("2022-08-01"),
                LocalTime.parse( "13:00"),
                "name",
                Theme.builder()
                        .name("카카오")
                        .desc("카카오 어드벤처")
                        .price(29_000)
                        .build()
        );

        Assertions.assertThatThrownBy(() -> reservationRepository.add(reservation))
                .isInstanceOf(NoSuchThemeException.class);
    }
}
