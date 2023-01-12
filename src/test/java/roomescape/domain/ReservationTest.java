package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.exception.RoomEscapeException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ReservationTest {

    @DisplayName("알맞은 시간이라면, Reservation을 생성할 수 있다.")
    @Test
    void create() {
        // given
        Long id = 1L;
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.of(11, 30);
        String name = "name";
        Theme theme = Themes.WANNA_GO_HOME;

        // when & then
        assertDoesNotThrow(() -> new Reservation(id, date, time, name, theme));
    }

    @DisplayName("알맞지 않은 시간에 대해서는 예외를 던진다.")
    @Test
    void createException() {
        // given
        Long id = 1L;
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.of(12, 56);
        String name = "name";
        Theme theme = Themes.WANNA_GO_HOME;

        // when & then
        assertThatCode(() -> new Reservation(id, date, time, name, theme))
                .isInstanceOf(RoomEscapeException.class);
    }
}