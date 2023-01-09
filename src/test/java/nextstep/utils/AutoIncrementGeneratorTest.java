package nextstep.utils;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AutoIncrementGeneratorTest {

    @Test
    void 도메인_별로_자동_증가하는_id값을_생성한다() {
        // when
        AutoIncrementGenerator.getId(Reservation.class);
        Long reservationId = AutoIncrementGenerator.getId(Reservation.class);
        Long themeId = AutoIncrementGenerator.getId(Theme.class);

        // then
        assertThat(reservationId).isEqualTo(2L);
        assertThat(themeId).isOne();
    }

}
