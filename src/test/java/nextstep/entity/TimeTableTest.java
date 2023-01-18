package nextstep.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TimeTableTest {




    @Test
    void timeTableSuccessTest(){
        Assertions.assertThat(TimeTable.contains(LocalTime.of(14, 30))).isTrue();
    }

    @Test
    void timeTableFailTest(){
        Assertions.assertThat(TimeTable.contains(LocalTime.of(14, 00))).isFalse();
    }



}