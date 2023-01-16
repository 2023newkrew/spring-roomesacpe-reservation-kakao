package domain;

import kakao.dto.request.CreateReservationRequest;
import kakao.error.ErrorCode;
import kakao.error.exception.DuplicatedReservationException;
import kakao.error.exception.IllegalCreateReservationRequestException;

import java.time.LocalDate;
import java.util.List;

public class ReservationValidator {
    private final List<Reservation> reservations;

    public ReservationValidator(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void validateForCreate(CreateReservationRequest request) {
        if (request.date.isBefore(LocalDate.now()) || request.date.isEqual(LocalDate.now()))
            throw new IllegalCreateReservationRequestException(ErrorCode.ILLEGAL_DATE);

        if (!reservations.isEmpty()) throw new DuplicatedReservationException();
    }
}
