package nextstep.domain.service;

import nextstep.domain.reservation.Reservation;
import nextstep.domain.service.exception.DuplicateSaveException;
import nextstep.domain.service.exception.ResourceNotFoundException;
import nextstep.repository.WebAppReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;

@Service
public class ReservationService {
    private final WebAppReservationRepo repo;

    @Autowired
    public ReservationService(WebAppReservationRepo repo) {
        this.repo = repo;
    }

    public long save(Reservation reservation) {
        if (this.isDuplicate(reservation)) {
            throw new DuplicateSaveException();
        }
        return repo.save(reservation);
    }

    public Reservation find(long id) {
        Reservation reservation = repo.findById(id);
        if (reservation == null) {
            throw new ResourceNotFoundException();
        }
        return reservation;
    }

    public void delete(long id) {
        if (repo.delete(id) == 0) {
            throw new ResourceNotFoundException();
        }
    }

    private boolean isDuplicate(Reservation reservation) {
        return repo.findByDateAndTime(Date.valueOf(reservation.getDate()), Time.valueOf(reservation.getTime())) > 0;
    }
}
