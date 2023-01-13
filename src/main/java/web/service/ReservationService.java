package web.service;

import org.springframework.stereotype.Service;
import web.domain.Reservation;
import web.domain.Theme;
import web.dto.request.ReservationRequestDTO;
import web.dto.response.ReservationIdDto;
import web.exception.DuplicatedReservationException;
import web.repository.ReservationJdbcRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationJdbcRepository reservationJdbcRepository;
    private final Theme defaultTheme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    public ReservationService(ReservationJdbcRepository reservationJdbcRepository) {
        this.reservationJdbcRepository = reservationJdbcRepository;
    }

    public ReservationIdDto createReservation(ReservationRequestDTO reservationRequestDTO) {
        Reservation reservation = reservationRequestDTO.toEntity(defaultTheme);

        validateReservation(reservation);

        return reservationJdbcRepository.createReservation(reservation);
    }

    private void validateReservation(Reservation reservation) {

        List<Long> ids = reservationJdbcRepository.findAllReservationWithDateAndTime(reservation);

        if (ids.size() > 0) {
            throw new DuplicatedReservationException();
        }
    }
}
