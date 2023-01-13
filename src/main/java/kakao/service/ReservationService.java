package kakao.service;

import kakao.model.request.ReservationRequest;
import kakao.model.response.ReservationResponse;

public interface ReservationService {
    public Long book(ReservationRequest reservationRequest);

    public ReservationResponse lookUp(Long id);

    public void cancel(Long id);
}
