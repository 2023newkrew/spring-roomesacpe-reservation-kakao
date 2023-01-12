package domain;

import kakao.dto.request.CreateReservationRequest;
import kakao.error.exception.DuplicatedReservationException;
import kakao.repository.ReservationJDBCRepository;
import kakao.repository.ThemeRepository;

public class ReservationFactory {
    private final ReservationJDBCRepository reservationJDBCRepository;

    public ReservationFactory(ReservationJDBCRepository reservationJDBCRepository) {
        this.reservationJDBCRepository = reservationJDBCRepository;
    }

    public Reservation createReservation(CreateReservationRequest request) {
        if (reservationJDBCRepository.findByDateAndTime(request.date, request.time).size() > 0) {
            throw new DuplicatedReservationException();
        }
        return Reservation.builder()
                .date(request.date)
                .time(request.time)
                .name(request.name)
                .theme(ThemeRepository.theme)
                .build();
    }
}
