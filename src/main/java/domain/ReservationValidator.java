package domain;

import kakao.dto.request.CreateReservationRequest;
import kakao.error.ErrorCode;
import kakao.error.exception.DuplicatedReservationException;
import kakao.error.exception.IllegalCreateReservationRequestException;
import kakao.repository.ReservationJDBCRepository;

import java.time.LocalDate;

public class ReservationValidator {

    private final ReservationJDBCRepository reservationJDBCRepository;

    public ReservationValidator(ReservationJDBCRepository reservationJDBCRepository) {
        this.reservationJDBCRepository = reservationJDBCRepository;
    }

    public void validateForCreate(CreateReservationRequest request) {
        if (request.date.isBefore(LocalDate.now()) || request.date.isEqual(LocalDate.now()))
            throw new IllegalCreateReservationRequestException(ErrorCode.ILLEGAL_DATE);

        if (!reservationJDBCRepository.findByDateAndTime(request.date, request.time).isEmpty()) {
            throw new DuplicatedReservationException();
        }
    }
}
