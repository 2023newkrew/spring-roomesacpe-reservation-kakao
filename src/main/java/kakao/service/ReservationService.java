package kakao.service;

import kakao.controller.request.ReservationRequest;
import kakao.controller.response.ReservationResponse;

public interface ReservationService {
    public Long book(ReservationRequest reservationRequest);

    public ReservationResponse lookUp(Long id);

    public void cancel(Long id);
}
