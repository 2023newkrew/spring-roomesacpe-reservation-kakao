package nextstep.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.model.Reservation;
import nextstep.model.Theme;
import nextstep.repository.ReservationRepository;
import nextstep.web.dto.ReservationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoomEscapeControllerTest extends AbstractControllerTest {

    @Autowired
    private ReservationRepository repository;

    @DisplayName("예약을 생성한다")
    @Test
    void createReservation() {
        // arrange
        long themeId = 테마를_생성한다("베루스홈", "베루스의 집", 50_000);

        String name = "예약_이름";
        LocalDate date = LocalDate.of(2022, 12, 14);
        LocalTime time = LocalTime.of(15, 5);

        // act
        Long id = 예약을_생성한다(themeId, name, date, time);

        // assert
        Reservation reservation = repository.findById(id).orElseThrow();
        assertThat(reservation.getDate()).isEqualTo(date);
        assertThat(reservation.getTime()).isEqualTo(time);
        assertThat(reservation.getName()).isEqualTo(name);
        assertThat(reservation.getTheme()).isEqualTo(new Theme(themeId, "베루스홈", "베루스의 집", 50_000));
    }

    @DisplayName("예약을 조회한다")
    @Test
    void getReservation() {
        // arrange
        long themeId = 테마를_생성한다("베루스홈", "베루스의 집", 50_000);

        String name = "예약_이름";
        LocalDate date = LocalDate.of(2022, 4, 3);
        LocalTime time = LocalTime.of(12, 15);
        Long id = 예약을_생성한다(themeId, name, date, time);

        // act
        ReservationResponse reservation = 예약을_조회한다(id);

        // assert
        assertThat(reservation.getDate()).isEqualTo(date);
        assertThat(reservation.getTime()).isEqualTo(time);
        assertThat(reservation.getName()).isEqualTo(name);
        assertThat(reservation.getThemeName()).isEqualTo("베루스홈");
        assertThat(reservation.getThemeDesc()).isEqualTo("베루스의 집");
        assertThat(reservation.getThemePrice()).isEqualTo(50_000);
    }

    @DisplayName("에약을 삭제한다")
    @Test
    void deleteReservation() {
        long themeId = 테마를_생성한다("베루스홈", "베루스의 집", 50_000);
        Long id = 예약을_생성한다(themeId, "취소될 예약", LocalDate.of(2023, 5, 29), LocalTime.of(8, 30));

        예약을_삭제한다(id);

        assertThat(repository.findById(id)).isEmpty();
    }
}
