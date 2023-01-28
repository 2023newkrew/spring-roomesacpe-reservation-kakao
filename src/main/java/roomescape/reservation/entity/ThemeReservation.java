package roomescape.reservation.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ThemeReservation {

    private final Long id;

    private final LocalDate date;

    private final LocalTime time;

    private final String name;

    private final Long themeId;

    private final String themeName;

    private final String themeDesc;

    private final int themePrice;
}
