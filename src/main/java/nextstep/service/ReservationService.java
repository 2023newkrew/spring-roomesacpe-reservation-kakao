package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.ReservationCreateRequest;
import nextstep.exception.DuplicateReservationException;
import nextstep.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    public static final Theme DEFAULT_THEME = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    private final ReservationRepository reservationRepository;


    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation add(ReservationCreateRequest request) {
        Reservation reservation = request.toReservation(DEFAULT_THEME);
        if (reservationRepository.hasReservationAt(reservation.getDate(), reservation.getTime().getHour())) {
            throw new DuplicateReservationException();
        }
        return reservationRepository.add(reservation);
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id);
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}
