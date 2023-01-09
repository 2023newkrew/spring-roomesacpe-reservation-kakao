package nextstep.main.java.nextstep.service;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.domain.ReservationCreateRequestDto;
import nextstep.main.java.nextstep.exception.DuplicateReservationException;
import nextstep.main.java.nextstep.exception.NoSuchReservationException;
import nextstep.main.java.nextstep.message.ExceptionMessage;
import nextstep.main.java.nextstep.repository.MemoryReservationRepository;
import nextstep.main.java.nextstep.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static nextstep.main.java.nextstep.message.ExceptionMessage.DUPLICATE_RESERVATION_MESSAGE;

@Service
public class ReservationService {
    private final ReservationRepository repository;
    private static long count = 1;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public void save(ReservationCreateRequestDto request) {
        Reservation reservation = new Reservation(count++, request.getDate(), request.getTime(), request.getName(), null);
        Optional<Reservation> duplicateReservation = repository.findByDateAndTime(reservation.getDate(), reservation.getTime());
        if(duplicateReservation.isPresent()){
            throw new DuplicateReservationException();
        }
        repository.save(reservation);
    }

    public Reservation findOneById(Long id) {
        return repository.findOne(id).orElseThrow(NoSuchReservationException::new);
    }

    public void deleteOneById(Long id) {
        repository.deleteOne(id);
    }
}
