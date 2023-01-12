package domain;

import kakao.dto.request.CreateReservationRequest;
import kakao.repository.ThemeRepository;

public class ReservationFactory {
    public Reservation createReservation(CreateReservationRequest request) {
        return Reservation.builder()
                .date(request.date)
                .time(request.time)
                .name(request.name)
                .theme(ThemeRepository.theme)
                .build();
    }
}
