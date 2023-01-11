package nextstep.main.java.nextstep.service.reservation;

import lombok.RequiredArgsConstructor;
import nextstep.main.java.nextstep.domain.reservation.ReservationFindResponse;
import nextstep.main.java.nextstep.domain.reservation.Reservation;
import nextstep.main.java.nextstep.domain.reservation.ReservationCreateRequest;
import nextstep.main.java.nextstep.domain.reservation.ReservationMapper;
import nextstep.main.java.nextstep.global.exception.exception.DuplicateReservationException;
import nextstep.main.java.nextstep.global.exception.exception.NoSuchReservationException;
import nextstep.main.java.nextstep.repository.reservation.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository repository;
    private final ReservationMapper reservationMapper;

    public Long save(ReservationCreateRequest request) {
        if (repository.existsByDateAndTime(request.getDate(), request.getTime())) {
            throw new DuplicateReservationException();
        }
        return repository.save(request);
    }

    public ReservationFindResponse findOneById(Long id) {
        Reservation reservation = repository.findOne(id).orElseThrow(NoSuchReservationException::new);
        return reservationMapper.reservationToFindResponse(reservation);
    }

    public void deleteOneById(Long id) {
        repository.deleteOne(id);
    }
}
