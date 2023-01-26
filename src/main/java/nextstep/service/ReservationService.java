package nextstep.service;

import nextstep.exception.ReservationDuplicateException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.model.Reservation;
import nextstep.model.Theme;
import nextstep.repository.ReservationRepository;
import nextstep.dto.ReservationRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation(ReservationRequest request) {
        if (reservationRepository.existsByDateAndTime(request.getDate(), request.getTime())) {
            throw new ReservationDuplicateException();
        }
        return reservationRepository.save(new Reservation(0L, request.getDate(), request.getTime(), request.getName(), request.getThemeId()));
    }

    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    public List<Reservation> getReservationByThemeId(Long themeId) {
        return reservationRepository.findByThemeId(themeId);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
