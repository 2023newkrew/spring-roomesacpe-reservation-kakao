package nextstep.service;

import nextstep.exception.ReservationDuplicateException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.model.Reservation;
import nextstep.model.Theme;
import nextstep.repository.JdbcTemplateReservationRepository;
import nextstep.repository.ReservationRepository;
import nextstep.web.dto.ReservationRequest;
import org.springframework.stereotype.Service;

@Service
public class RoomEscapeService {

    private final ReservationRepository reservationRepository;

    public RoomEscapeService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation(ReservationRequest request) {
        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
        if (reservationRepository.existsByDateAndTime(request.getDate(), request.getTime())) {
            throw new ReservationDuplicateException();
        }
        return reservationRepository.save(
                new Reservation(0L, request.getDate(), request.getTime(), request.getName(), theme));
    }

    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
