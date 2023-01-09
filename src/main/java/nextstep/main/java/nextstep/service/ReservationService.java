package nextstep.main.java.nextstep.service;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.domain.ReservationCreateRequestDto;
import nextstep.main.java.nextstep.repository.MemoryReservationRepository;
import nextstep.main.java.nextstep.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            throw new RuntimeException();
        }
        repository.save(reservation);
    }

    public Reservation findOneById(Long id) {
        return repository.findOne(id);
    }

    public void deleteOneById(Long id) {
        repository.deleteOne(id);
    }
}
