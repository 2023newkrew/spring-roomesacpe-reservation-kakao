package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.exception.DuplicateReservationException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.mapper.EntityMapper;
import nextstep.mapper.EntityToResponseMapper;
import nextstep.repository.reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(@Qualifier("jdbcTemplate") ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public <R, T> R createReservation(T request, EntityMapper<T, Reservation, R> entityMapper) throws DuplicateReservationException {
        Reservation reservation = entityMapper.requestToEntity(request);

        if (reservationRepository.hasReservationAt(reservation.getDate(), reservation.getTime())) {
            throw new DuplicateReservationException();
        }

        Reservation createdReservation = reservationRepository.add(reservation);

        return entityMapper.entityToResponse(createdReservation);
    }

    public <R> R findReservation(Long id, EntityToResponseMapper<Reservation, R> mapper) throws ReservationNotFoundException {
        return mapper.entityToResponse(reservationRepository.get(id));
    }

    public void cancelReservation(Long id) {
        reservationRepository.delete(id);
    }
}
