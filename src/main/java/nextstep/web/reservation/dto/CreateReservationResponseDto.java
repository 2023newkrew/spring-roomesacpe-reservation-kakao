package nextstep.web.reservation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nextstep.domain.Reservation;

@Getter
@RequiredArgsConstructor
public class CreateReservationResponseDto {
    private final String location;

    public static CreateReservationResponseDto from(Long id) {
        return new CreateReservationResponseDto(Reservation.BASE_URL + "/" + id);
    }
}
