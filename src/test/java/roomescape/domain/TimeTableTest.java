package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TimeTableTest {

    @DisplayName("타임테이블에 존재하는 시간인지 검사할 수 있다. - true인 경우")
    @Test
    void isExistTrue() {
        // given
        LocalTime localTime = LocalTime.of(11, 30);

        // when & then
        assertThat(TimeTable.isExist(localTime)).isTrue();
    }

    @DisplayName("타임테이블에 존재하는 시간인지 검사할 수 있다. - false인 경우")
    @Test
    void isExistFalse() {
        // given
        LocalTime localTime = LocalTime.of(11, 28);

        // when & then
        assertThat(TimeTable.isExist(localTime)).isFalse();
    }
}