package roomescape.reservation.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.reservation.entity.ThemeReservation;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationResponseDTO {

    private final Long id;

    private final String date;

    private final String time;

    private final String name;

    private final Long themeId;

    private final String themeName;

    private final String themeDesc;

    private final int themePrice;

    public static ReservationResponseDTO from(ThemeReservation reservation) {
        return ReservationResponseDTO.builder()
                .id(reservation.getId())
                .date(reservation.getDate().toString())
                .time(reservation.getTime().toString())
                .name(reservation.getName())
                .themeId(reservation.getThemeId())
                .themeDesc(reservation.getThemeDesc())
                .themeName(reservation.getName())
                .themePrice(reservation.getThemePrice())
                .build();
    }
}
