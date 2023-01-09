package web.service;

import org.springframework.stereotype.Service;
import web.dto.ReservationRequestDto;
import web.repository.ReservationRepository;


@Service
public class RoomEscapeService {

    private final ReservationRepository reservationRepository;

    public RoomEscapeService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public long reservation(ReservationRequestDto requestDto) {
        return reservationRepository.save(requestDto.toEntity());
    }
}
