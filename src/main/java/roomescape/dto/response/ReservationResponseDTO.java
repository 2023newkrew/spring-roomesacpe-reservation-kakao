package roomescape.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.domain.Reservation;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationResponseDTO {

    private final Long id;
    private final String date;
    private final String time;
    private final String name;
    private final Long themeId;

    public static ReservationResponseDTO from(Reservation reservation) {
        return ReservationResponseDTO.builder()
                .id(reservation.getId())
                .date(reservation.getDate().toString())
                .time(reservation.getTime().toString())
                .name(reservation.getName())
                .themeId(reservation.getThemeId())
                .build();
    }
}
