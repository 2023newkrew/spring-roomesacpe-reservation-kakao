package nextstep.domain.service;

import nextstep.domain.dto.PostReservationDTO;
import nextstep.domain.reservation.Reservation;
import nextstep.domain.service.exception.DuplicateSaveException;
import nextstep.domain.service.exception.ResourceNotFoundException;
import nextstep.domain.theme.Theme;
import nextstep.repository.WebAppReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationService {
    private final WebAppReservationRepo repo;

    @Autowired
    public ReservationService(WebAppReservationRepo repo) {
        this.repo = repo;
    }

    public long saveReservation(PostReservationDTO dto) {
        Reservation reservation = new Reservation(
                LocalDate.parse(dto.getLocalDate()),
                LocalTime.parse(dto.getLocalTime()),
                dto.getName(),
                new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000));
        if (repo.findByDateAndTime(Date.valueOf(reservation.getDate()), Time.valueOf(reservation.getTime())) > 0) {
            throw new DuplicateSaveException();
        }
        return repo.save(reservation);
    }

    public Reservation findReservation(long id) {
        Reservation reservation = repo.findById(id);
        if (reservation == null) {
            throw new ResourceNotFoundException();
        }
        return reservation;
    }

    public void deleteReservation(long id) {
        if (repo.delete(id) == 0) {
            throw new ResourceNotFoundException();
        }
    }
}
