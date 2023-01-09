package nextstep.utils;

import nextstep.dto.CreateReservationRequest;
import nextstep.exception.InvalidCreateReservationRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ReservationRequestValidator {

    public void validateCreateRequest(CreateReservationRequest createReservationRequest) {
        if (StringUtils.isEmpty(createReservationRequest.getDate())
                || StringUtils.isEmpty(createReservationRequest.getTime())
                || StringUtils.isEmpty(createReservationRequest.getName())) {
            throw new InvalidCreateReservationRequestException();
        }
    }

}
