package domain;

import kakao.error.ErrorCode;
import kakao.error.exception.DuplicatedReservationException;
import kakao.error.exception.IllegalCreateReservationRequestException;
import kakao.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationValidator {
    private final ReservationRepository reservationRepository;

    public ReservationValidator(ReservationRepository repository) {
        this.reservationRepository = repository;
    }

    public void validateForCreate(LocalDate date, LocalTime time) {
        if (date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now()))
            throw new IllegalCreateReservationRequestException(ErrorCode.ILLEGAL_DATE);

        if (!reservationRepository.findByDateAndTime(date, time).isEmpty()) throw new DuplicatedReservationException();
    }
}
