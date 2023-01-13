package nextstep.service;

import nextstep.domain.dto.CreateReservationDto;
import nextstep.domain.reservation.Reservation;
import nextstep.exception.DeleteReservationFailureException;
import nextstep.exception.DuplicateTimeReservationException;
import nextstep.exception.IllegalReservationTimeException;
import nextstep.exception.NoReservationException;
import nextstep.repository.reservation.ReservationRepository;
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
        checkReservationTime(LocalTime.parse(createReservationDto.getTime()));
        checkDuplicateReservation(
                createReservationDto.getThemeId(),
                Date.valueOf(createReservationDto.getDate()),
                Time.valueOf(createReservationDto.getTime())
        );
        return reservationRepository.add(reservation);
    }

    private void checkReservationTime(LocalTime time) {
        if (time.getSecond() != 0) {
            throw new IllegalReservationTimeException();
        }
        if (time.getMinute() != 30 && time.getMinute() != 0) {
            throw new IllegalReservationTimeException();
        }
    }

    private void checkDuplicateReservation(Long themId, Date date, Time time) {
        int duplicatedCount = reservationRepository.countByDateAndTime(themId, date, time);
        if (duplicatedCount > 0) {
            throw new DuplicateTimeReservationException();
        }
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
