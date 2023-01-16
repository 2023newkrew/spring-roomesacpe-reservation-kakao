package nextstep.main.java.nextstep.service;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.domain.ReservationCreateRequestDto;
import nextstep.main.java.nextstep.exception.exception.DuplicateReservationException;
import nextstep.main.java.nextstep.exception.exception.NoSuchReservationException;
import nextstep.main.java.nextstep.exception.exception.NoSuchThemeException;
import nextstep.main.java.nextstep.repository.ReservationRepository;
import nextstep.main.java.nextstep.repository.ThemeRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Reservation save(ReservationCreateRequestDto request) {
        themeRepository.findById(request.getThemeId())
                .orElseThrow(NoSuchThemeException::new);
        validateThemeExist(request);
        if (reservationRepository.existsByDateAndTime(request.getDate(), request.getTime())) {
            throw new DuplicateReservationException();
        }
        Reservation reservation = new Reservation(request.getDate(), request.getTime(), request.getName(), request.getThemeId());
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

    protected void validateThemeExist(ReservationCreateRequestDto requestDto) {
        themeRepository.findById(requestDto.getThemeId())
                .orElseThrow(NoSuchThemeException::new);
    }
}
