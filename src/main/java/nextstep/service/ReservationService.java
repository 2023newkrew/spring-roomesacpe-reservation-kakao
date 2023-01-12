package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.dto.request.CreateReservationRequest;
import nextstep.dto.response.ReservationConsoleResponse;
import nextstep.dto.response.ReservationResponse;
import nextstep.exception.DuplicateReservationException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.repository.reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(@Qualifier("jdbcTemplate") ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationResponse createReservationForWeb(CreateReservationRequest request) throws DuplicateReservationException {
        return ReservationResponse.fromEntity(createReservation(request));
    }

    public ReservationConsoleResponse createReservationForConsole(CreateReservationRequest request) throws DuplicateReservationException {
        return ReservationConsoleResponse.fromEntity(createReservation(request));
    }

    private Reservation createReservation(CreateReservationRequest request) throws DuplicateReservationException {
        if (reservationRepository.hasReservationAt(request.getDate(), request.getTime())) {
            throw new DuplicateReservationException();
        }

        Reservation reservation = request.toEntity();
        return reservationRepository.add(reservation);
    }

    public ReservationResponse findReservationForWeb(Long id) throws ReservationNotFoundException {
        return ReservationResponse.fromEntity(findReservation(id));
    }

    public ReservationConsoleResponse findReservationForConsole(Long id) throws ReservationNotFoundException {
        return ReservationConsoleResponse.fromEntity(findReservation(id));
    }

    private Reservation findReservation(Long id) throws ReservationNotFoundException {
        return reservationRepository.get(id);
    }

    public void cancelReservation(Long id) {
        reservationRepository.delete(id);
    }
}
