package nextstep.domain.service;

import nextstep.domain.reservation.Reservation;
import nextstep.controller.exception.DuplicateDataException;
import nextstep.controller.exception.DataNotFoundException;
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
            throw new DuplicateDataException();
        }
        return repo.save(reservation);
    }

    public Reservation find(long id) {
        Reservation reservation = repo.findById(id);
        if (reservation == null) {
            throw new DataNotFoundException();
        }
        return reservation;
    }

    public void delete(long id) {
        if (repo.delete(id) == 0) {
            throw new DataNotFoundException();
        }
    }

    private boolean isDuplicate(Reservation reservation) {
        return repo.findByDateAndTimeAndTheme(
                Date.valueOf(reservation.getDate()),
                Time.valueOf(reservation.getTime()),
                reservation.getThemeId()) > 0;
    }
}
