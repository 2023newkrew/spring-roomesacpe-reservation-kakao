package nextstep.reservations.dto.reservation;

import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
public class TimeTable {
    public static final ArrayList<LocalTime> values;

    static {
        values = new ArrayList<>(Arrays.asList("09:00", "11:00", "13:00", "15:00", "17:00", "19:00", "21:00")
                .stream()
                .map(LocalTime::parse)
                .collect(Collectors.toList())
        );
    }
}
