package nextstep.service;

import nextstep.exception.ReservationDuplicateException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.model.Reservation;
import nextstep.model.Theme;
import nextstep.repository.JdbcTemplateReservationRepository;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ThemeRepository;
import nextstep.web.dto.ReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomEscapeService {

    private final ReservationRepository reservationRepository;
    private ThemeRepository themeRepository;

    public RoomEscapeService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Autowired
    public RoomEscapeService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Reservation createReservation(ReservationRequest request) {
        Theme theme = themeRepository.findById(request.getThemeId())
                .orElseThrow(() -> new RuntimeException());

        if (reservationRepository.existsByDateAndTime(request.getDate(), request.getTime())) {
            throw new ReservationDuplicateException();
        }
        return reservationRepository.save(
                new Reservation(request.getDate(), request.getTime(), request.getName(), theme));
    }

    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
