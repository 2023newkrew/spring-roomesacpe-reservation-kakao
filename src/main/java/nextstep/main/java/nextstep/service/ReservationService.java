package nextstep.main.java.nextstep.service;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.domain.ReservationCreateRequestDto;
import nextstep.main.java.nextstep.domain.Theme;
import nextstep.main.java.nextstep.exception.exception.DuplicateReservationException;
import nextstep.main.java.nextstep.exception.exception.NoSuchReservationException;
import nextstep.main.java.nextstep.exception.exception.NoSuchThemeException;
import nextstep.main.java.nextstep.repository.ReservationRepository;
import nextstep.main.java.nextstep.repository.ThemeRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    public static final Theme DEFAULT_THEME = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
    public static final Long DEFAULT_THEME_ID = 1L;
    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Reservation save(ReservationCreateRequestDto request) {
        themeRepository.findById(request.getThemeId())
                .orElseThrow(NoSuchThemeException::new);
        Reservation reservation = new Reservation(request.getDate(), request.getTime(), request.getName(), request.getThemeId());
        if (reservationRepository.existsByDateAndTime(reservation.getDate(), reservation.getTime())) {
            throw new DuplicateReservationException();
        }
        return reservationRepository.save(reservation);
    }

    public Reservation findOneById(Long id) {
        return reservationRepository.findOne(id)
                .orElseThrow(NoSuchReservationException::new);
    }

    public void deleteOneById(Long id) {
        if (!reservationRepository.deleteOne(id)) {
            throw new NoSuchReservationException();
        }
    }
}
