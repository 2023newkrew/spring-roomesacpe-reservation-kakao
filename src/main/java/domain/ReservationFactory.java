package domain;

import kakao.dto.request.CreateReservationRequest;
import kakao.error.exception.DuplicatedReservationException;
import kakao.repository.ReservationJDBCRepository;
import kakao.repository.ThemeRepository;
import org.springframework.stereotype.Component;

@Component
public class ReservationFactory {
    private final ReservationJDBCRepository reservationJDBCRepository;

    public ReservationFactory(ReservationJDBCRepository reservationJDBCRepository) {
        this.reservationJDBCRepository = reservationJDBCRepository;
    }

    public Reservation createReservation(CreateReservationRequest request) {
        if (reservationJDBCRepository.findByDateAndTime(request.date, request.time).size() > 0) {
            throw new DuplicatedReservationException();
        }

        return new Reservation(request.date, request.time, request.name, ThemeRepository.theme);
    }
}
