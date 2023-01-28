package kakao.service;

import kakao.controller.request.ReservationRequest;
import kakao.controller.response.ReservationResponse;

public interface ReservationService {
    Long book(ReservationRequest reservationRequest);

    ReservationResponse lookUp(Long id);

    void cancel(Long id);
}
