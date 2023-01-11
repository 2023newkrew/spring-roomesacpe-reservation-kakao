package nextstep.main.java.nextstep.service.reservation;

import nextstep.main.java.nextstep.domain.reservation.Reservation;
import nextstep.main.java.nextstep.domain.reservation.ReservationCreateRequestDto;
import nextstep.main.java.nextstep.domain.theme.Theme;
import nextstep.main.java.nextstep.global.exception.exception.DuplicateReservationException;
import nextstep.main.java.nextstep.global.exception.exception.NoSuchReservationException;
import nextstep.main.java.nextstep.repository.reservation.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public Long save(ReservationCreateRequestDto request) {
        if (repository.existsByDateAndTime(request.getDate(), request.getTime())) {
            throw new DuplicateReservationException();
        }
        return repository.save(request);
    }

    public Reservation findOneById(Long id) {
        return repository.findOne(id).orElseThrow(NoSuchReservationException::new);
    }

    public void deleteOneById(Long id) {
        repository.deleteOne(id);
    }
}
