package nextstep.mapper;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.console.request.CreateReservationConsoleRequest;
import nextstep.dto.console.response.ReservationConsoleResponse;
import org.springframework.stereotype.Component;

@Component
public class ReservationConsoleMapper implements EntityMapper<CreateReservationConsoleRequest, Reservation, ReservationConsoleResponse> {

    @Override
    public ReservationConsoleResponse entityToResponse(Reservation reservation) {
        return ReservationConsoleResponse.of(
                reservation.getId(),
                reservation.getDate().toString(),
                reservation.getTime().toString(),
                reservation.getName(),
                reservation.getTheme().getName(),
                reservation.getTheme().getDesc(),
                reservation.getTheme().getPrice()
        );
    }

    @Override
    public Reservation requestToEntity(CreateReservationConsoleRequest request) {
        return Reservation.of(
                request.getDate(),
                request.getTime(),
                request.getName(),
                Theme.DEFAULT_THEME
        );
    }
}
