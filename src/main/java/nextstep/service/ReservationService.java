package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.dto.console.request.CreateReservationConsoleRequest;
import nextstep.dto.web.request.CreateReservationRequest;
import nextstep.dto.console.response.ReservationConsoleResponse;
import nextstep.dto.web.response.ReservationResponse;
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
        return ReservationResponse.fromEntity(createReservation(request.toEntity()));
    }

    public ReservationConsoleResponse createReservationForConsole(CreateReservationConsoleRequest request) throws DuplicateReservationException {
        return ReservationConsoleResponse.fromEntity(createReservation(request.toEntity()));
    }

    private Reservation createReservation(Reservation reservation) throws DuplicateReservationException {
        if (reservationRepository.hasReservationAt(reservation.getDate(), reservation.getTime())) {
            throw new DuplicateReservationException();
        }

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
