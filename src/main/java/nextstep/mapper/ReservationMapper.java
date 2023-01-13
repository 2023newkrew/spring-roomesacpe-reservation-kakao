package nextstep.mapper;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.web.request.CreateReservationRequest;
import nextstep.dto.web.response.ReservationResponse;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper implements EntityMapper<CreateReservationRequest, Reservation, ReservationResponse> {

    @Override
    public Reservation requestToEntity(CreateReservationRequest request) {
        return Reservation.of(
                request.getDateInLocalDate(),
                request.getTimeInLocalTime(),
                request.getName(),
                Theme.of(request.getThemeId(), null, null, null)
        );
    }

    @Override
    public ReservationResponse entityToResponse(Reservation reservation) {
        return ReservationResponse.of(
                reservation.getId(),
                reservation.getDate().toString(),
                reservation.getTime().toString(),
                reservation.getName(),
                reservation.getTheme().getName(),
                reservation.getTheme().getDesc(),
                reservation.getTheme().getPrice()
        );
    }
}