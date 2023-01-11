package nextstep.utils;

import nextstep.dto.request.CreateReservationRequest;
import nextstep.error.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static nextstep.error.ErrorType.INVALID_RESERVATION_REQUEST_DATA;

@Component
public class ReservationRequestValidator {

    public void validateCreateRequest(CreateReservationRequest createReservationRequest) {
        if (StringUtils.isEmpty(createReservationRequest.getDate())
                || StringUtils.isEmpty(createReservationRequest.getTime())
                || StringUtils.isEmpty(createReservationRequest.getName())) {
            throw new ApplicationException(INVALID_RESERVATION_REQUEST_DATA);
        }
    }

}
