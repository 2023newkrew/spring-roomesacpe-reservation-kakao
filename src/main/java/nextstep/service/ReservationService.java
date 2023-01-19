package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.ReservationCreateRequest;
import nextstep.exception.DuplicateReservationException;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ThemeRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    public static final Theme DEFAULT_THEME = new Theme(null, "워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;


    public ReservationService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Reservation save(ReservationCreateRequest request) {
        Theme theme = themeRepository.findById(request.getThemeId());
        Reservation reservation = request.toReservation(theme);
        if (reservationRepository.hasReservationAt(reservation.getDate(), reservation.getTime().getHour())) {
            throw new DuplicateReservationException();
        }
        return reservationRepository.save(reservation);
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id);
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}
