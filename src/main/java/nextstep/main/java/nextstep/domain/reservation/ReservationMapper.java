package nextstep.main.java.nextstep.domain.reservation;

import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    public ReservationFindResponse reservationToFindResponse(Reservation reservation) {
        return ReservationFindResponse.builder()
                .id(reservation.getId())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .name(reservation.getName())
                .themeName(reservation.getTheme().getName())
                .themeDesc(reservation.getTheme().getDesc())
                .themePrice(reservation.getTheme().getPrice())
                .build();
    }
}
