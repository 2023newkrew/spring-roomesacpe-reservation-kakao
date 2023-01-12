package web.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import web.domain.Reservation;
import web.domain.Theme;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationResponseDTO {

    private final Long id;
    private final String date;
    private final String time;
    private final String name;
    private final String themeName;
    private final String themeDesc;
    private final Integer themePrice;

    public static ReservationResponseDTO from(Reservation reservation) {
        Theme theme = reservation.getTheme();

        return new ReservationResponseDTO(reservation.getId(), reservation.getDate().toString(),
                reservation.getTime().toString(),
                reservation.getName(), theme.getName(), theme.getDesc(), theme.getPrice());
    }
}
