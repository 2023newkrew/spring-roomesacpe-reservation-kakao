package nextstep.service;

import nextstep.domain.theme.Theme;
import nextstep.domain.dto.CreateReservationDto;
import nextstep.domain.dto.GetReservationDto;
import nextstep.domain.reservation.Reservation;
import nextstep.exception.DeleteReservationFailureException;
import nextstep.exception.DuplicateTimeReservationException;
import nextstep.exception.NoReservationException;
import nextstep.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public long addReservation(CreateReservationDto createReservationDto) {
        Reservation reservation = Reservation.createReservation(createReservationDto);
        int duplicatedCount = reservationRepository.countByDateAndTime(
                Date.valueOf(reservation.getDate()),
                Time.valueOf(reservation.getTime())
        );
        if (duplicatedCount > 0) {
            throw new DuplicateTimeReservationException();
        }
        return reservationRepository.add(reservation);
    }

    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(NoReservationException::new);
    }

    public void deleteReservation(Long id) {
        int result = reservationRepository.delete(id);
        if(result == 0) {
            throw new DeleteReservationFailureException();
        }
    }

}
