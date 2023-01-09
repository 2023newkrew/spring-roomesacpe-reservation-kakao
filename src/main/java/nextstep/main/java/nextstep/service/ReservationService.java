package nextstep.main.java.nextstep.service;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.domain.ReservationCreateRequestDto;
import nextstep.main.java.nextstep.repository.MemoryReservationRepository;
import nextstep.main.java.nextstep.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository repository;
    private static long count = 1;

    public ReservationService(ReservationRepository repository) {
        this.repository = new MemoryReservationRepository();
    }

    public void save(ReservationCreateRequestDto request) {
        Reservation reservation = new Reservation(count++, request.getDate(), request.getTime(), request.getName(), null);
        repository.save(reservation);
    }
}
