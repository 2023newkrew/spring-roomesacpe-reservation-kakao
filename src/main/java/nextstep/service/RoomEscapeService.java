package nextstep.service;

import nextstep.Reservation;
import nextstep.dto.request.CreateReservationRequest;
import nextstep.dto.response.ReservationResponse;
import nextstep.exception.DuplicateReservationException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomEscapeService {
    private final ReservationRepository reservationRepository;


    @Autowired
    public RoomEscapeService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationResponse add(CreateReservationRequest request) throws DuplicateReservationException {

        if (reservationRepository.hasReservationAt(request.getDate(), request.getTime())) {
            throw new DuplicateReservationException();
        }

        Reservation reservation = request.toEntity();
        return ReservationResponse.fromEntity(reservationRepository.add(reservation));
    }

    public ReservationResponse get(Long id) throws ReservationNotFoundException {
        return ReservationResponse.fromEntity(reservationRepository.get(id));
    }

    public void delete(Long id) {
        reservationRepository.delete(id);
    }
}
